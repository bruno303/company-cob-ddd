package com.bso.companycob.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.application.dto.ContractDTO;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;

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
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractAmountUpdater.updateAmount(dto);

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(1)).updateDebtAmount();
    }

    @Test
    void testCallUpdateDebtWhenContractIsNotPresent() {
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.empty());

        var exception = assertThrows(ContractNotFoundException.class,
                () -> contractAmountUpdater.updateAmount(dto));

        assertThat(exception.getContractId()).isEqualByComparingTo(dto.getContractId());

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(0)).updateDebtAmount();
    }

    private Contract createContractMock() {
        return Mockito.mock(Contract.class);
    }
    
}
