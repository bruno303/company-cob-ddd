package com.bso.companycob.application.service;

import java.util.Optional;

import com.bso.companycob.application.dto.ContractDTO;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractAmountUpdater {

    private final ContractRepository contractRepository;
    
    public void updateAmount(ContractDTO contractDto) {
        Optional<Contract> contractOpt = contractRepository.findById(contractDto.getContractId());
        ContractNotFoundException.throwsWhen(contractOpt.isEmpty(), contractDto.getContractId());
        contractOpt.get().updateDebtAmount();
    }

}
