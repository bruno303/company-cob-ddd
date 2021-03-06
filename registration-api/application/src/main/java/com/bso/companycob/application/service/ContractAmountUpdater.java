package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.ContractDTO;
import com.bso.companycob.application.lock.ContractLockeable;
import com.bso.companycob.application.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

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
