package com.bso.companycob.infrastructure.utils;

import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.infrastructure.entities.Quota;
import com.bso.companycob.infrastructure.repositories.PersistenceBankRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceContractRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceQuotaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Fixture {

    @Autowired
    private PersistenceContractRepository contractRepository;

    @Autowired
    private PersistenceQuotaRepository quotaRepository;

    @Autowired
    private PersistenceBankRepository bankRepository;
    
    public Contract save(Contract contract) {
        return contractRepository.save(contract);
    }

    public Quota save(Quota quota) {
        return quotaRepository.save(quota);
    }

    public Bank save(Bank bank) {
        return bankRepository.save(bank);
    }
}
