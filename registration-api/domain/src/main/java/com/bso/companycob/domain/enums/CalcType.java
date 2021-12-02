package com.bso.companycob.domain.enums;

import java.util.stream.Stream;

public enum CalcType {
    
    DEFAULT(1);

    private final int value;

    CalcType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static CalcType fromValue(int value) {
        return Stream.of(CalcType.values())
            .filter(c -> c.getValue() == value)
            .findAny()
            .orElse(DEFAULT);
    }
}
