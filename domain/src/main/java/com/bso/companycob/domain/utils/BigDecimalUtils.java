package com.bso.companycob.domain.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    private BigDecimalUtils() {}
    
    public static boolean firstIsSmallerThanSecond(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) < 0;
    }

    public static boolean firstIsGreaterThanSecond(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) > 0;
    }

    public static boolean equals(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    public static boolean isNegative(BigDecimal number) {
        return firstIsSmallerThanSecond(number, BigDecimal.ZERO);
    }
}
