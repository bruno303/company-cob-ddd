package com.bso.companycob.application.core.dto.bank;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankCreationDTO {
    private final String name;
    private final BigDecimal interestRate;
}