package com.bso.companycob.domain.enums;

import java.util.stream.Stream;

public enum QuotaStatus {
    
    OPEN(0),
    PAID(1);

    private final int value;

    QuotaStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public static QuotaStatus fromValue(int value) {
        return Stream.of(QuotaStatus.values())
            .filter(q -> q.getValue() == value)
            .findAny()
            .orElseThrow(() -> new RuntimeException("Quota status with value " + value + " not found!"));
    }
}
