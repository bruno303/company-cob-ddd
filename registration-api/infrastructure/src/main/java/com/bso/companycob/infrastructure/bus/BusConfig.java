package com.bso.companycob.infrastructure.bus;

import com.bso.companycob.application.bus.handler.ContractCreationRequestHandler;
import com.bso.companycob.application.bus.handler.ContractGetAllRequestHandler;
import com.bso.companycob.application.bus.handler.ContractGetRequestHandler;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import com.bso.companycob.infrastructure.transaction.TransactionExecutorFactory;
import com.bso.dracko.mediator.springboot.EnableDrackoMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDrackoMediator
@Configuration
@RequiredArgsConstructor
public class BusConfig {

    private final TransactionExecutorFactory transactionExecutorFactory;

    @Bean
    public ContractCreationRequestHandler contractCreationRequestHandler(ContractFactory contractFactory,
                                                                         ContractWriterRepository contractRepository,
                                                                         EventRaiser eventRaiser,
                                                                         QuotaFactory quotaFactory,
                                                                         LockManager lockManager) {
        return new ContractCreationRequestHandler(contractFactory, contractRepository, eventRaiser, quotaFactory,
                lockManager, transactionExecutorFactory.createInstance());
    }

    @Bean
    public ContractGetAllRequestHandler contractGetAllRequestHandler(ContractReaderRepository contractReaderRepository) {
        return new ContractGetAllRequestHandler(contractReaderRepository);
    }

    @Bean
    public ContractGetRequestHandler contractGetRequestHandler(ContractReaderRepository contractReaderRepository) {
        return new ContractGetRequestHandler(contractReaderRepository);
    }
}
