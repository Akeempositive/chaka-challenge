package com.positive.chaka.challenge;

import java.math.BigDecimal;
import java.util.Date;

public class Util {

    public static final String AMOUNT_KEY = "amount";
    public static final String TIMESTAMP_KEY = "_timestampOffset";
    public static final String TIMESTAMP_KEY2 = "timestamp";

    public static final long A_MINUTE_INTERVAL = 60000;

    public static boolean overOneMinute(Date date){
        return date.getTime() <= new Date().getTime() - A_MINUTE_INTERVAL;
    }

    public static BigDecimal toBigDecimalWithDp(double number, int dp){
        return new BigDecimal(number).setScale(dp, BigDecimal.ROUND_HALF_UP);
    }
}
