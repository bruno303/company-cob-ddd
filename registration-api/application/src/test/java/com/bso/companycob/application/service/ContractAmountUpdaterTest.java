package com.bso.companycob.application.service;

import com.bso.companycob.application.FakeLockManager;
import com.bso.companycob.application.dto.ContractDTO;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ContractAmountUpdaterTest {

    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final LockManager lockManager = new FakeLockManager();
    private ContractAmountUpdater contractAmountUpdater;

    @BeforeEach
    public void init() {
        contractAmountUpdater = new ContractAmountUpdater(contractRepository, lockManager);
    }

    @Test
    void testCallUpdateDebtWhenContractIsPresent() {
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractAmountUpdater.updateAmount(dto);

        Mockito.verify(contractRepository, Mockito.times(1)).findById(dto.getContractId());
        Mockito.verify(contract, Mockito.times(1)).updateDebtAmount();
    }

    @Test
    void testCallUpdateDebtWhenContractIsNotPresent() {
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.empty());

        var exception = assertThrows(ContractNotFoundException.class,
                () -> contractAmountUpdater.updateAmount(dto));

        Assertions.assertThat(exception.getContractId()).isEqualByComparingTo(dto.getContractId());

        Mockito.verify(contractRepository, Mockito.times(1)).findById(dto.getContractId());
        Mockito.verify(contract, Mockito.times(0)).updateDebtAmount();
    }

    private Contract createContractMock() {
        var contractMock = Mockito.mock(Contract.class);
        Mockito.when(contractMock.getNumber()).thenReturn("XPTO");
        return contractMock;
    }
    
}
