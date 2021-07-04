package com.bso.companycob.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentDTO {
    
    private final UUID contractId;
    private BigDecimal amount;

    public PaymentDTO(UUID contractId, BigDecimal amount) {
        this.contractId = contractId;
        this.amount = amount;
    }

    public UUID getContractId() {
        return contractId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

}
