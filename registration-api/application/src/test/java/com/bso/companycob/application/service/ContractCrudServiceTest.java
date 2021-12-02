package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.contract.ContractCreationDTO;
import com.bso.companycob.application.dto.quota.QuotaCreationDTO;
import com.bso.companycob.application.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.factory.contract.ContractFactory;
import com.bso.companycob.application.factory.quota.QuotaFactory;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ContractCrudServiceTest {

    private ContractCrudService contractCrudService;
    private final ContractFactory contractFactoryMock = Mockito.mock(ContractFactory.class);
    private final Contract contractMockResponse = Mockito.mock(Contract.class);
    private final ContractRepository contractRepositoryMock = Mockito.mock(ContractRepository.class);
    private final EventRaiser eventRaiserMock = Mockito.mock(EventRaiser.class);
    private final QuotaFactory quotaFactory = Mockito.mock(QuotaFactory.class);

    @BeforeEach
    public void setup() {
        contractCrudService = new ContractCrudService(contractFactoryMock, contractRepositoryMock, eventRaiserMock, quotaFactory);
    }

    @Test
    public void testCreatingContractMustCallFactoryAndRepositoryAndEventRaiser() {
        when(contractFactoryMock.create(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        when(contractRepositoryMock.saveAndFlush(Mockito.any(Contract.class)))
            .thenReturn(contractMockResponse);

        var dto = new ContractCreationDTO("XPTO", LocalDate.now(), UUID.randomUUID(), Collections.emptyList(), CalcType.DEFAULT);
        contractCrudService.createContract(dto);

        verify(contractFactoryMock, times(1)).create(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(UUID.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        verify(contractRepositoryMock, times(1)).saveAndFlush(Mockito.any(Contract.class));
        verify(eventRaiserMock, times(1)).raise(Mockito.any(ContractCreatedEvent.class));
        verify(quotaFactory, times(0)).create(Mockito.any(QuotaCreationDTO.class));
    }

    @Test
    public void testGetAllCallFindAllMethodFromRepository() {
        when(contractRepositoryMock.findAll()).thenReturn(Collections.emptyList());
        List<Contract> all = contractCrudService.findAll();
        assertThat(all).hasSize(0);

        verify(contractRepositoryMock, times(1)).findAll();
    }
}
