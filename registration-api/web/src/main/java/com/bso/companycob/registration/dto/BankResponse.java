package com.bso.companycob.registration.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class BankResponse {
    private final UUID id;
    private final String name;
    private final BigDecimal interestRate;
}
