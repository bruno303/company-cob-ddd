package com.bso.companycob.domain.enums;

public enum CalcType {
    
    DEFAULT(1);

    private int value;

    CalcType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
