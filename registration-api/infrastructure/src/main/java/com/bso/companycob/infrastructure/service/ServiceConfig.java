package com.bso.companycob.infrastructure.service;

import com.bso.companycob.application.factory.BankFactory;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.application.service.BankCrudService;
import com.bso.companycob.application.service.ContractAmountUpdater;
import com.bso.companycob.application.service.ContractPaymentReceiver;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.BankRepository;
import com.bso.companycob.domain.repositories.ContractRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    public BankCrudService bankCrudService(BankFactory bankFactory, BankRepository bankRepository) {
        return new BankCrudService(bankFactory, bankRepository);
    }

    @Bean
    public ContractAmountUpdater contractAmountUpdater(ContractRepository contractRepository, LockManager lockManager) {
        return new ContractAmountUpdater(contractRepository, lockManager);
    }

    @Bean
    public ContractPaymentReceiver contractPaymentReceiver(ContractRepository contractRepository, EventRaiser eventRaiser) {
        return new ContractPaymentReceiver(contractRepository, eventRaiser);
    }
}
