package com.bso.companycob.infrastructure.bus;

import com.bso.companycob.application.bus.handler.ContractCreationRequestHandler;
import com.bso.companycob.application.bus.handler.ContractGetAllRequestHandler;
import com.bso.companycob.application.bus.handler.ContractGetRequestHandler;
import com.bso.companycob.application.bus.request.ContractCreationRequest;
import com.bso.companycob.application.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.bus.request.ContractGetRequest;
import com.bso.companycob.application.bus.response.ContractCreationResponse;
import com.bso.companycob.application.bus.response.ContractGetAllResponse;
import com.bso.companycob.application.bus.response.ContractGetResponse;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import com.bso.dracko.mediator.contract.RequestHandler;
import com.bso.dracko.mediator.springboot.EnableDrackoMediator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@EnableDrackoMediator
@Configuration
@RequiredArgsConstructor
public class BusConfig {

    @Bean
    public RequestHandler<ContractCreationRequest, ContractCreationResponse> contractCreationRequestHandler(ContractFactory contractFactory,
                                                                         ContractWriterRepository contractRepository,
                                                                         EventRaiser eventRaiser,
                                                                         QuotaFactory quotaFactory,
                                                                         LockManager lockManager) {
        var originalHandler = new ContractCreationRequestHandler(contractFactory,
                contractRepository, eventRaiser, quotaFactory, lockManager);

        return new SpringRequestHandlerProxy<>(originalHandler);
    }

    @Bean
    public RequestHandler<ContractGetAllRequest, List<ContractGetAllResponse>> contractGetAllRequestHandler(ContractReaderRepository contractReaderRepository) {
        var originalHandler = new ContractGetAllRequestHandler(contractReaderRepository);
        return new SpringRequestHandlerProxy<>(originalHandler);
    }

    @Bean
    public RequestHandler<ContractGetRequest, Optional<ContractGetResponse>> contractGetRequestHandler(ContractReaderRepository contractReaderRepository) {
        var originalHandler = new ContractGetRequestHandler(contractReaderRepository);
        return new SpringRequestHandlerProxy<>(originalHandler);
    }
}
