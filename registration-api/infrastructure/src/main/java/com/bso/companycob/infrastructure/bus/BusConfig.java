package com.bso.companycob.infrastructure.bus;

import com.bso.companycob.application.bus.request.handler.ContractCreationRequestHandler;
import com.bso.companycob.application.bus.request.handler.ContractGetAllRequestHandler;
import com.bso.companycob.application.bus.request.handler.ContractGetRequestHandler;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import com.bso.dracko.mediator.springboot.EnableDrackoMediator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableDrackoMediator
@Configuration
public class BusConfig {

    @Bean
    public ContractCreationRequestHandler contractCreationRequestHandler(ContractFactory contractFactory,
                                                                         ContractWriterRepository contractRepository,
                                                                         EventRaiser eventRaiser,
                                                                         QuotaFactory quotaFactory,
                                                                         LockManager lockManager) {
        return new ContractCreationRequestHandler(contractFactory, contractRepository, eventRaiser, quotaFactory, lockManager);
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
