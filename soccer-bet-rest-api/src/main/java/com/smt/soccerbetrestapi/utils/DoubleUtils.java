package com.smt.soccerbetrestapi.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleUtils {

    public static double round(double value, int decimalPoint) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPoint, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
