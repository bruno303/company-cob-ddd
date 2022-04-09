package com.bso.companycob.application.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.application.core.dto.ContractDTO;
import com.bso.companycob.application.core.lock.ContractLockeable;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.application.model.lock.Lockeable;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ContractAmountUpdaterTest {

    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final LockManager lockManager = Mockito.mock(LockManager.class);
    private ContractAmountUpdater contractAmountUpdater;

    @BeforeEach
    public void init() {
        contractAmountUpdater = new ContractAmountUpdater(contractRepository, lockManager);
    }

    @Test
    void testCallUpdateDebtWhenContractIsPresent() {
        ArgumentCaptor<Lockeable> lockeableCaptor = ArgumentCaptor.forClass(Lockeable.class);
        ArgumentCaptor<Runnable> runnableCaptor = ArgumentCaptor.forClass(Runnable.class);
        ContractDTO dto = new ContractDTO(UUID.randomUUID());
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractAmountUpdater.updateAmount(dto);

        Mockito.verify(lockManager).lockAndConsume(lockeableCaptor.capture(), runnableCaptor.capture());

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(lockManager, times(1)).lockAndConsume(any(ContractLockeable.class), any());

        // capture the method running inside lock, call it and then verify if it was as expected
        runnableCaptor.getValue().run();
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
