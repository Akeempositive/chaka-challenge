package com.positive.chaka.challenge;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Service
public class TransactionRepository {

    public List<Transaction> transactionList = new ArrayList<>();

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public void save(Transaction transaction){
        transactionList.add(transaction);
    }

    public List<Double> getAllLastMinuteTransactionsAmounts(){
        return transactionList.stream()
                .filter(item -> !Util.overOneMinute(item.getTimestamp()))
                .map(Transaction::getAmount).map(BigDecimal::doubleValue)
                .collect(Collectors.toList());
    }

    public void deleteAllTransactions(){
        transactionList = new ArrayList<>();
    }
}
