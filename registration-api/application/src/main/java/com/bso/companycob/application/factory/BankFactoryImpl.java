package com.bso.companycob.application.factory;

import com.bso.companycob.domain.entity.bank.Bank;

import java.math.BigDecimal;
import java.util.UUID;

public class BankFactoryImpl implements BankFactory {

    @Override
    public Bank create(String name, BigDecimal interestRate) {
        return new Bank(UUID.randomUUID(), name, interestRate);
    }
    
    @Override
    public Bank create(UUID id, String name, BigDecimal interestRate) {
        return new Bank(id, name, interestRate);
    }

}
