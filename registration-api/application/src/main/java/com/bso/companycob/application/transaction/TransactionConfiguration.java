package com.bso.companycob.application.transaction;

import lombok.Data;

@Data
public class TransactionConfiguration {

    public static TransactionConfiguration getDefault() {
        return new TransactionConfiguration();
    }

    private Propagation propagation = Propagation.REQUIRED;
    private Isolation isolation = Isolation.DEFAULT;
    private int timeout = -1;
    private boolean readOnly = false;

    public enum Propagation {
        REQUIRED,
        SUPPORTS,
        REQUIRES_NEW
    }

    public enum Isolation {
        DEFAULT,
        READ_UNCOMMITTED,
        READ_COMMITTED
    }
}
