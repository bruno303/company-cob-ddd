package com.bso.companycob.application.core.service;

import com.bso.companycob.application.lock.ContractLockeable;
import com.bso.companycob.application.dto.ContractDTO;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.application.lock.Lockeable;
import com.bso.companycob.application.service.ContractAmountUpdater;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

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

        Mockito.verify(contractRepository, Mockito.times(1)).findById(dto.getContractId());
        Mockito.verify(lockManager, Mockito.times(1)).lockAndConsume(ArgumentMatchers.any(ContractLockeable.class), ArgumentMatchers.any());

        // capture the method running inside lock, call it and then verify if it was as expected
        runnableCaptor.getValue().run();
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
        return Mockito.mock(Contract.class);
    }
    
}
