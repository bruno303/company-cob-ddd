package com.bso.companycob.domain.exception;

import java.util.UUID;

public class BankNotFoundException extends DomainException {
    private final UUID bankId;

    public BankNotFoundException(UUID bankId) {
        super(String.format("Bank with id '%s' not found.", bankId));
        this.bankId = bankId;
    }

    public UUID getBankId() {
        return bankId;
    }

    public static void throwsWhen(boolean condition, UUID contractId) {
        if (condition) throw new BankNotFoundException(contractId);
    }
}
