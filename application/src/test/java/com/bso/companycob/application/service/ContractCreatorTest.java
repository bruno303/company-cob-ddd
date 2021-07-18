package com.bso.companycob.application.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;

import com.bso.companycob.application.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.factory.contract.ContractFactory;
import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ContractCreatorTest {

    private ContractCreator contractCreator;
    private final ContractFactory contractFactoryMock = Mockito.mock(ContractFactory.class);
    private final Contract contractMockResponse = Mockito.mock(Contract.class);
    private final ContractRepository contractRepositoryMock = Mockito.mock(ContractRepository.class);
    private final EventRaiser eventRaiserMock = Mockito.mock(EventRaiser.class);

    @BeforeEach
    public void setup() {
        contractCreator = new ContractCreator(contractFactoryMock, contractRepositoryMock, eventRaiserMock);
    }

    @Test
    public void testCreatingContractMustCallFactoryAndRepositoryAndEventRaiser() {
        when(contractFactoryMock.create(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(Bank.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT)))
            .thenReturn(contractMockResponse);

        when(contractRepositoryMock.saveAndFlush(Mockito.any(Contract.class)))
            .thenReturn(contractMockResponse);

        var dto = new ContractCreator.ContractCreationDTO("XPTO", LocalDate.now(), Mockito.mock(Bank.class), Collections.emptyList(), CalcType.DEFAULT);
        contractCreator.createContract(dto);

        verify(contractFactoryMock, times(1)).create(Mockito.anyString(), Mockito.any(LocalDate.class), Mockito.any(Bank.class), Mockito.anyList(), Mockito.eq(CalcType.DEFAULT));
        verify(contractRepositoryMock, times(1)).saveAndFlush(Mockito.any(Contract.class));
        verify(eventRaiserMock, times(1)).raise(Mockito.any(ContractCreatedEvent.class));
    }
    
}
