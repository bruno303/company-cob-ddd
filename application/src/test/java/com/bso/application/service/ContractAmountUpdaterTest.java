package com.bso.application.service;

import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;

import com.bso.application.dto.ContractDTO;
import com.bso.application.repository.ContractRepository;
import com.bso.companycob.domain.entity.Contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContractAmountUpdaterTest {

    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private ContractAmountUpdater contractAmountUpdater;

    @BeforeEach
    public void init() {
        contractAmountUpdater = new ContractAmountUpdater(contractRepository);
    }

    @Test
    void testCallUpdateDebtWhenContractIsPresent() {
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContract();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractAmountUpdater.updateAmount(dto);

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(1)).updateDebtAmount();
    }

    @Test
    void testCallUpdateDebtWhenContractIsNotPresent() {
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContract();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.empty());

        contractAmountUpdater.updateAmount(dto);

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(0)).updateDebtAmount();
    }

    private Contract createContract() {
        return Mockito.mock(Contract.class);
    }
    
}
