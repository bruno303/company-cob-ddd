package com.bso.companycob.domain.enums;

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
}
