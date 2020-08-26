package com.positive.chaka.challenge;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

    private BigDecimal amount;

    private Date timestamp;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timstamp) {
        this.timestamp = timstamp;
    }

    public Transaction(String amount, Long timestamp, Long ct) throws ParseException {
        this.amount = BigDecimal.valueOf(Double.parseDouble(amount));
        this.timestamp = new Date(ct + timestamp);
    }

    public Transaction(BigDecimal amount, Date timestamp){
        this.amount = amount;
        this.timestamp = timestamp;
    }
}
