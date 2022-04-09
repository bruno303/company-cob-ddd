package com.bso.companycob.infrastructure.mapper;

import com.bso.companycob.infrastructure.entities.Bank;
import org.springframework.stereotype.Component;

@Component
public class BankMapper {

    public com.bso.companycob.domain.entity.bank.Bank toDomainBank(Bank bank) {
        return new com.bso.companycob.domain.entity.bank.Bank(bank.getId(), bank.getName(), bank.getInterestRate());
    }

    public Bank toPersistenceBank(com.bso.companycob.domain.entity.bank.Bank bank) {
        var persistenceBank = new Bank();
        persistenceBank.setId(bank.getId());
        persistenceBank.setName(bank.getName());
        persistenceBank.setInterestRate(bank.getInterestRate());

        return persistenceBank;
    }

}
