package com.bso.companycob.application.core.service;

import com.bso.companycob.application.core.lock.ContractLockeable;
import com.bso.companycob.application.model.dto.ContractDTO;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class ContractAmountUpdater {

    private final ContractRepository contractRepository;
    private final LockManager lockManager;
    
    public void updateAmount(ContractDTO contractDto) {
        Optional<Contract> contractOpt = contractRepository.findById(contractDto.getContractId());
        ContractNotFoundException.throwsWhen(contractOpt.isEmpty(), contractDto.getContractId());

        Contract contract = contractOpt.get();
        ContractLockeable contractLockeable = new ContractLockeable(contract);
        lockManager.lockAndConsume(contractLockeable, contract::updateDebtAmount);
    }

}
