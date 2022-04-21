package com.bso.companycob.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class PaymentDTO {
    private UUID contractId;
    private BigDecimal amount;
}
