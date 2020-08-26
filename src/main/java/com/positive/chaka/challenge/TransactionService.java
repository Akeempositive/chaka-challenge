package com.positive.chaka.challenge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public ResponseEntity addToTransaction(Map<String, Object> object) {
        Long currentTime = System.currentTimeMillis();
        if (object == null) return ResponseEntity.status(400).body(null);
        Transaction transaction;
        try {
            transaction = toTransaction(object, currentTime);
        } catch (UnParseabeJsonException ex) {
            return ResponseEntity.status(422).body(null);
        }
        int valid = valid(transaction);
        if (valid == 201) {
            transactionRepository.save(transaction);
        }
        return ResponseEntity.status(valid).body(null);
    }

    private Long toDate(Object o) {
        try {
            return Long.parseLong(o.toString());
        } catch (NumberFormatException ex) {
            try {
                return new SimpleDateFormat().parse(o.toString()).getTime();
            } catch (ParseException e) {

            }
        }
        return null;
    }

    private Transaction toTransaction(Map<String, Object> transaction, Long ct) throws UnParseabeJsonException {
        try {
            Long timeOffset = 0L;
            for (String key : transaction.keySet()) {
                if (key.contains(Util.TIMESTAMP_KEY2)) timeOffset = toDate(transaction.get(key));
            }
            if (timeOffset < -1 * Util.A_MINUTE_INTERVAL) {
                throw new NullPointerException();
            }
            return new Transaction(transaction.get(Util.AMOUNT_KEY).toString(), timeOffset, ct);
        } catch (NullPointerException ex) {
            return null;
        } catch (NumberFormatException ex) {
            throw new UnParseabeJsonException();
        } catch (Exception e) {
            e.printStackTrace();
            return new Transaction(BigDecimal.ONE.negate(), null);
        }
    }

    private int valid(Transaction transaction) {
        if (transaction == null) {
            return 204;
        } else if (transaction.getAmount().doubleValue() < 0 && transaction.getTimestamp() == null) {
            return 400;
        } else if (transaction.getTimestamp().after(new Date())) {
            return 422;
        } else {
            return 201;
        }
    }

    public ResponseEntity<Statistics> getStatistics() {
        List<Double> amounts = transactionRepository.getAllLastMinuteTransactionsAmounts();
        Statistics statistics = new Statistics();
        statistics.setCount(amounts.size());
        statistics.setSum(getSum(amounts.stream()));
        statistics.setAvg(getAverage(amounts.stream()));
        statistics.setMax(getMax(amounts.stream()));
        statistics.setMin(getMin(amounts.stream()));
        return ResponseEntity.status(200).body(statistics);
    }

    public ResponseEntity deleteAllTransactions() {
        transactionRepository.deleteAllTransactions();
        return ResponseEntity.status(204).body(null);
    }

    private BigDecimal getAverage(Stream<Double> amounts) {
        return Util.toBigDecimalWithDp(amounts.mapToDouble(Double::doubleValue).average().orElse(0.00), 2);
    }

    private BigDecimal getSum(Stream<Double> amounts) {
        return Util.toBigDecimalWithDp(amounts.reduce(0.0, (acc, item) -> acc + item), 2);
    }

    private BigDecimal getMax(Stream<Double> amounts) {
        return Util.toBigDecimalWithDp(amounts.max(Double::compareTo).orElse(0.00), 2);
    }

    private BigDecimal getMin(Stream<Double> amounts) {
        return Util.toBigDecimalWithDp(amounts.min(Double::compareTo).orElse(0.00), 2);
    }

    class UnParseabeJsonException extends Exception {
        public UnParseabeJsonException() {
            super("Invalid Json Values");
        }
    }

}
