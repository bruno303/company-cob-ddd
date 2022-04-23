package com.bso.companycob.application.bus.handler;

import com.bso.companycob.application.FakeLockManager;
import com.bso.companycob.application.FakeTransactionExecutor;
import com.bso.companycob.application.bus.request.ContractCreationRequest;
import com.bso.companycob.application.event.ContractCreatedEvent;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.application.transaction.TransactionExecutor;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

class ContractCreationRequestHandlerTest {

    private ContractCreationRequestHandler handler;
    private final ContractFactory contractFactoryMock = Mockito.mock(ContractFactory.class);
    private final Contract contractMockResponse = Mockito.mock(Contract.class);
    private final ContractWriterRepository contractWriterRepositoryMock = Mockito.mock(ContractWriterRepository.class);
    private final EventRaiser eventRaiserMock = Mockito.mock(EventRaiser.class);
    private final QuotaFactory quotaFactory = Mockito.mock(QuotaFactory.class);
    private final LockManager lockManager = new FakeLockManager();
    private final TransactionExecutor transactionExecutor = new FakeTransactionExecutor();

    @BeforeEach
    public void setup() {
        handler = new ContractCreationRequestHandler(contractFactoryMock, contractWriterRepositoryMock, eventRaiserMock,
                quotaFactory, lockManager, transactionExecutor);
    }

    @Test
    void testCreatingContractMustCallFactoryAndRepositoryAndEventRaiserAndLockManager() {
        final String number = "XPTO";
        Mockito.when(contractFactoryMock.create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        Mockito.when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.empty());

        Mockito.when(contractWriterRepositoryMock.saveAndFlush(ArgumentMatchers.any(Contract.class)))
            .thenReturn(contractMockResponse);

        var cmd = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        handler.handle(cmd);

        Mockito.verify(contractFactoryMock, Mockito.times(1)).create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(Contract.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).findByNumber(number);
        Mockito.verify(eventRaiserMock, Mockito.times(1)).raise(ArgumentMatchers.any(ContractCreatedEvent.class));
        Mockito.verify(quotaFactory, Mockito.times(0)).create(ArgumentMatchers.any(ContractCreationRequest.QuotaData.class));
    }

    @Test
    void testCreatingContractWhenContractAlreadyExists() {
        final String number = "XPTO";
        Mockito.when(contractWriterRepositoryMock.findByNumber(number)).thenReturn(Optional.of(contractMockResponse));

        var request = new ContractCreationRequest(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        ContractAlreadyExistsException ex = Assertions.catchThrowableOfType(() -> handler.handle(request), ContractAlreadyExistsException.class);
        Assertions.assertThat(ex).isExactlyInstanceOf(ContractAlreadyExistsException.class);

        Mockito.verify(contractFactoryMock, Mockito.times(0)).create(Mockito.anyString(), ArgumentMatchers.any(LocalDate.class), ArgumentMatchers.any(UUID.class), Mockito.anyList(), Mockito.any(CalcType.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(0)).saveAndFlush(ArgumentMatchers.any(Contract.class));
        Mockito.verify(contractWriterRepositoryMock, Mockito.times(1)).findByNumber(number);
        Mockito.verify(eventRaiserMock, Mockito.times(0)).raise(ArgumentMatchers.any(ContractCreatedEvent.class));
        Mockito.verify(quotaFactory, Mockito.times(0)).create(ArgumentMatchers.any(ContractCreationRequest.QuotaData.class));
    }
}
