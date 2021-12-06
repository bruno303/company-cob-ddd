package com.bso.companycob.application.factory.bank;

import java.math.BigDecimal;
import java.util.UUID;

import com.bso.companycob.domain.entity.bank.Bank;
import org.springframework.stereotype.Component;

@Component
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
