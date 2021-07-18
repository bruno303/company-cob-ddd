package com.bso.companycob.application.service;

import java.util.Optional;

import com.bso.companycob.application.dto.PaymentDTO;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.springframework.stereotype.Service;

@Service
public class ContractPaymentReceiver {

    private final ContractRepository contractRepository;

    public ContractPaymentReceiver(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }
    
    public void receivePayment(PaymentDTO paymentDto) {
        Optional<Contract> contractOpt = contractRepository.findById(paymentDto.getContractId());
        contractOpt.ifPresent(c -> c.receivePayment(paymentDto.getAmount()));
    }
}
