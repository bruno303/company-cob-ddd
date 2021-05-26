package com.bso.application.service;

import java.util.Optional;

import com.bso.application.dto.PaymentDTO;
import com.bso.application.repository.ContractRepository;
import com.bso.companycob.domain.entity.Contract;

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
