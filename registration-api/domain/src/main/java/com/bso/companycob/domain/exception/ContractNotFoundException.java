package com.bso.companycob.domain.exception;

import java.util.UUID;

public class ContractNotFoundException extends DomainException {
    private final UUID contractId;

    public ContractNotFoundException(UUID contractId) {
        super(String.format("Contract with id '%s' not found.", contractId));
        this.contractId = contractId;
    }

    public UUID getContractId() {
        return contractId;
    }

    public static void throwsWhen(boolean condition, UUID contractId) {
        if (condition) throw new ContractNotFoundException(contractId);
    }
}
