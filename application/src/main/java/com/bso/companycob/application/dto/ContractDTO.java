package com.bso.companycob.application.dto;

import java.util.UUID;

public class ContractDTO {
    private final UUID contractId;

    public ContractDTO(UUID contractId) {
        this.contractId = contractId;
    }

    public UUID getContractId() {
        return contractId;
    }
}
