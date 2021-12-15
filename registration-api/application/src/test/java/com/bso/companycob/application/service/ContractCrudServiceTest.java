package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.contract.ContractCreationDTO;
import com.bso.companycob.application.dto.quota.QuotaCreationDTO;
import com.bso.companycob.application.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.factory.contract.ContractFactory;
import com.bso.companycob.application.factory.quota.QuotaFactory;
import com.bso.companycob.application.lock.ContractLockeable;
import com.bso.companycob.application.model.lock.Callable;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ContractCrudServiceTest {

    private ContractCrudService contractCrudService;
    private final ContractFactory contractFactoryMock = Mockito.mock(ContractFactory.class);
    private final Contract contractMockResponse = Mockito.mock(Contract.class);
    private final ContractRepository contractRepositoryMock = Mockito.mock(ContractRepository.class);
    private final EventRaiser eventRaiserMock = Mockito.mock(EventRaiser.class);
    private final QuotaFactory quotaFactory = Mockito.mock(QuotaFactory.class);
    private final LockManager lockManagerMock = Mockito.mock(LockManager.class);

    @BeforeEach
    public void setup() {
        contractCrudService = new ContractCrudService(contractFactoryMock, contractRepositoryMock, eventRaiserMock, quotaFactory, lockManagerMock);
    }

    @Test
    public void testCreatingContractMustCallFactoryAndRepositoryAndEventRaiserAndLockManager() {
        final String number = "XPTO";
        ArgumentCaptor<Callable<Contract>> callableCaptor = ArgumentCaptor.forClass(Callable.class);

        when(contractFactoryMock.create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        when(contractRepositoryMock.findByNumber(number)).thenReturn(Optional.empty());

        when(contractRepositoryMock.saveAndFlush(any(Contract.class)))
            .thenReturn(contractMockResponse);

        when(lockManagerMock.lockAndProcess(any(ContractLockeable.class), any(Callable.class)))
                .thenReturn(contractMockResponse);

        var dto = new ContractCreationDTO(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        contractCrudService.createContract(dto);

        verify(lockManagerMock, times(1)).lockAndProcess(any(ContractLockeable.class), callableCaptor.capture());

        // call the callable captured. Then do the verifys bellow
        callableCaptor.getValue().call();

        verify(contractFactoryMock, times(1)).create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        verify(contractRepositoryMock, times(1)).saveAndFlush(any(Contract.class));
        verify(contractRepositoryMock, times(1)).findByNumber(number);
        verify(eventRaiserMock, times(1)).raise(any(ContractCreatedEvent.class));
        verify(quotaFactory, times(0)).create(any(QuotaCreationDTO.class));
    }

    @Test
    public void testCreatingContractWhenContractAlreadyExists() {
        final String number = "XPTO";
        ArgumentCaptor<Callable<Contract>> callableCaptor = ArgumentCaptor.forClass(Callable.class);

        when(contractRepositoryMock.findByNumber(number)).thenReturn(Optional.of(contractMockResponse));

        when(lockManagerMock.lockAndProcess(any(ContractLockeable.class), any(Callable.class)))
                .thenReturn(contractMockResponse);

        var dto = new ContractCreationDTO(number, LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        contractCrudService.createContract(dto);

        verify(lockManagerMock, times(1)).lockAndProcess(any(ContractLockeable.class), callableCaptor.capture());

        // call the callable captured. Then do the verifys bellow
        Callable<Contract> callable = callableCaptor.getValue();
        ContractAlreadyExistsException ex = catchThrowableOfType(callable::call,
                ContractAlreadyExistsException.class);

        assertThat(ex).isExactlyInstanceOf(ContractAlreadyExistsException.class);

        verify(contractFactoryMock, times(0)).create(Mockito.anyString(), any(LocalDate.class), any(UUID.class), Mockito.anyList(), Mockito.any(CalcType.class));
        verify(contractRepositoryMock, times(0)).saveAndFlush(any(Contract.class));
        verify(contractRepositoryMock, times(1)).findByNumber(number);
        verify(eventRaiserMock, times(0)).raise(any(ContractCreatedEvent.class));
        verify(quotaFactory, times(0)).create(any(QuotaCreationDTO.class));
    }

    @Test
    public void testGetAllCallFindAllMethodFromRepository() {
        when(contractRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<Contract> all = contractCrudService.findAll();
        assertThat(all).isEmpty();

        verify(contractRepositoryMock, times(1)).findAll();
    }
}
