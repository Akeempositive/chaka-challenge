package com.positive.chaka.challenge;

import java.math.BigDecimal;

public class Statistics {

    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal min;
    private BigDecimal max;
    private long count;

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object obj) {
        Statistics test = (Statistics) obj;
        return  test.count == count &&
                test.max.equals(max) &&
                test.min.equals(min) &&
                test.avg.equals(avg) &&
                test.sum.equals(sum);

//        return  test.count == count &&
//                test.max.doubleValue()==max.doubleValue() &&
//                test.min.doubleValue() == min.doubleValue() &&
//                test.avg.doubleValue() == avg.doubleValue() &&
//                test.sum.doubleValue() ==sum.doubleValue();
    }
}
