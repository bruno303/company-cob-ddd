package com.bso.companycob.application.core.handlers.command;

import com.bso.companycob.application.bus.request.ContractCreationRequest;
import com.bso.companycob.application.bus.response.ContractCreationResponse;
import com.bso.companycob.application.event.ContractCreatedEvent;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.bus.request.handler.ContractCreationRequestHandler;
import com.bso.companycob.application.lock.ContractLockeable;
import com.bso.companycob.application.lock.Callable;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

public class ContractCreationRequestHandlerTest {

    private ContractCreationRequestHandler handler;
    private final ContractFactory contractFactoryMock = Mockito.mock(ContractFactory.class);
    private final Contract contractMockResponse = Mockito.mock(Contract.class);
    private final ContractCreationResponse contractCreationMockResponse = Mockito.mock(ContractCreationResponse.class);
    private final ContractWriterRepository contractWriterRepositoryMock = Mockito.mock(ContractWriterRepository.class);
    private final EventRaiser eventRaiserMock = Mockito.mock(EventRaiser.class);
    private final QuotaFactory quotaFactory = Mockito.mock(QuotaFactory.class);
    private final LockManager lockManagerMock = Mockito.mock(LockManager.class);

    @BeforeEach
    public void setup() {
        handler = new ContractCreationRequestHandler(contractFactoryMock, contractWriterRepositoryMock, eventRaiserMock, quotaFactory, lockManagerMock);
    }

    @Test
    public void testCreatingContractMustCallFactoryAndRepositoryAndEventRaiserAndLockManager() {
        final String number = "XPTO";
        ArgumentCaptor<Callable<ContractCreationResponse>> callableCaptor = ArgumentCaptor.forClass(Callable.class);

        Mockito.when(contractFactoryMock.create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        Mockito.when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.empty());

        Mockito.when(contractWriterRepositoryMock.saveAndFlush(ArgumentMatchers.any(Contract.class)))
            .thenReturn(contractMockResponse);

        Mockito.when(lockManagerMock.lockAndProcess(ArgumentMatchers.any(ContractLockeable.class), ArgumentMatchers.any(Callable.class)))
                .thenReturn(contractCreationMockResponse);

        var cmd = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        handler.handle(cmd);

        Mockito.verify(lockManagerMock, Mockito.times(1)).lockAndProcess(ArgumentMatchers.any(ContractLockeable.class), callableCaptor.capture());

        // call the runnable captured. Then do the verifys bellow
        callableCaptor.getValue().call();

        Mockito.verify(contractFactoryMock, Mockito.times(1)).create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(Contract.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).findByNumber(number);
        Mockito.verify(eventRaiserMock, Mockito.times(1)).raise(ArgumentMatchers.any(ContractCreatedEvent.class));
        Mockito.verify(quotaFactory, Mockito.times(0)).create(ArgumentMatchers.any(ContractCreationRequest.QuotaData.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatingContractWhenContractAlreadyExists() {
        final String number = "XPTO";
        ArgumentCaptor<Callable<?>> callableCaptor = ArgumentCaptor.forClass(Callable.class);

        Mockito.when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.of(contractMockResponse));

        Mockito.when(lockManagerMock.lockAndProcess(ArgumentMatchers.any(ContractLockeable.class), ArgumentMatchers.any(Callable.class)))
                .thenReturn(contractCreationMockResponse);

        var request = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        handler.handle(request);

        Mockito.verify(lockManagerMock, Mockito.times(1)).lockAndProcess(ArgumentMatchers.any(ContractLockeable.class), callableCaptor.capture());

        // call the callable captured. Then do the verifys bellow
        Callable<?> callable = callableCaptor.getValue();
        ContractAlreadyExistsException ex = Assertions.catchThrowableOfType(callable::call, ContractAlreadyExistsException.class);

        Assertions.assertThat(ex).isExactlyInstanceOf(ContractAlreadyExistsException.class);

        Mockito.verify(contractFactoryMock, Mockito.times(0)).create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.any(CalcType.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(0)).saveAndFlush(ArgumentMatchers.any(Contract.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).findByNumber(number);
        Mockito.verify(eventRaiserMock, Mockito.times(0)).raise(ArgumentMatchers.any(ContractCreatedEvent.class));
        Mockito.verify(quotaFactory, Mockito.times(0)).create(ArgumentMatchers.any(ContractCreationRequest.QuotaData.class));
    }
}
