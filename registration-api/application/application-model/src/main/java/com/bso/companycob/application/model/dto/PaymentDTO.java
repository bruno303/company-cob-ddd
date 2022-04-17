package com.bso.companycob.application.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentDTO {
    private final UUID contractId;
    private final BigDecimal amount;
}
