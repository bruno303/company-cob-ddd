package com.bso.companycob.application.model.factory;

import java.math.BigDecimal;
import java.util.UUID;

import com.bso.companycob.domain.entity.bank.Bank;

public interface BankFactory {
    
    Bank create(String name, BigDecimal interestRate);
    Bank create(UUID id, String name, BigDecimal interestRate);

}
