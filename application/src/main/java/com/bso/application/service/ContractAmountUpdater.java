package com.bso.application.service;

import java.util.Optional;

import com.bso.application.dto.ContractDTO;
import com.bso.application.repository.ContractRepository;
import com.bso.companycob.domain.entity.Contract;

import org.springframework.stereotype.Service;

@Service
public class ContractAmountUpdater {

    private final ContractRepository contractRepository;

    public ContractAmountUpdater(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
    
    public void updateAmount(ContractDTO contractDto) {
        Optional<Contract> contractOpt = contractRepository.findById(contractDto.getContractId());
        contractOpt.ifPresent(Contract::updateDebtAmount);
    }

}
