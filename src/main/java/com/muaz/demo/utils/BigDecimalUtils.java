package com.muaz.demo.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    public static BigDecimal getRateDiscount(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).movePointLeft(2);
    }
}
