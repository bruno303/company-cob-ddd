package com.bso.companycob.application.dto.bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankCreationDTO {
    private String name;
    private BigDecimal interestRate;
}
