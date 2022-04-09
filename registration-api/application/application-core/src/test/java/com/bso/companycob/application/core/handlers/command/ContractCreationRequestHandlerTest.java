package com.bso.companycob.application.core.handlers.command;

import com.bso.companycob.application.core.bus.request.ContractCreationRequest;
import com.bso.companycob.application.core.bus.response.ContractCreationResponse;
import com.bso.companycob.application.core.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.core.factory.contract.ContractFactory;
import com.bso.companycob.application.core.factory.quota.QuotaFactory;
import com.bso.companycob.application.core.handlers.requests.ContractCreationRequestHandler;
import com.bso.companycob.application.core.lock.ContractLockeable;
import com.bso.companycob.application.model.lock.Callable;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        when(contractFactoryMock.create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.empty());

        when(contractWriterRepositoryMock.saveAndFlush(any(Contract.class)))
            .thenReturn(contractMockResponse);

        when(lockManagerMock.lockAndProcess(any(ContractLockeable.class), any(Callable.class)))
                .thenReturn(contractCreationMockResponse);

        var cmd = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        handler.handle(cmd);

        verify(lockManagerMock, times(1)).lockAndProcess(any(ContractLockeable.class), callableCaptor.capture());

        // call the runnable captured. Then do the verifys bellow
        callableCaptor.getValue().call();

        verify(contractFactoryMock, times(1)).create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        verify(contractWriterRepositoryMock, times(1)).saveAndFlush(any(Contract.class));
        verify(contractWriterRepositoryMock, times(1)).findByNumber(number);
        verify(eventRaiserMock, times(1)).raise(any(ContractCreatedEvent.class));
        verify(quotaFactory, times(0)).create(any(ContractCreationRequest.QuotaData.class));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCreatingContractWhenContractAlreadyExists() {
        final String number = "XPTO";
        ArgumentCaptor<Callable> callableCaptor = ArgumentCaptor.forClass(Callable.class);

        when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.of(contractMockResponse));

        when(lockManagerMock.lockAndProcess(any(ContractLockeable.class), any(Callable.class)))
                .thenReturn(contractCreationMockResponse);

        var request = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        handler.handle(request);

        verify(lockManagerMock, times(1)).lockAndProcess(any(ContractLockeable.class), callableCaptor.capture());

        // call the callable captured. Then do the verifys bellow
        Callable callable = callableCaptor.getValue();
        ContractAlreadyExistsException ex = catchThrowableOfType(callable::call, ContractAlreadyExistsException.class);

        assertThat(ex).isExactlyInstanceOf(ContractAlreadyExistsException.class);

        verify(contractFactoryMock, times(0)).create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.any(CalcType.class));
        verify(contractWriterRepositoryMock, times(0)).saveAndFlush(any(Contract.class));
        verify(contractWriterRepositoryMock, times(1)).findByNumber(number);
        verify(eventRaiserMock, times(0)).raise(any(ContractCreatedEvent.class));
        verify(quotaFactory, times(0)).create(any(ContractCreationRequest.QuotaData.class));
    }
}
