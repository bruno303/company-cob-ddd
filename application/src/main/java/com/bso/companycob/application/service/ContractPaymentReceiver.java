package com.bso.companycob.application.service;

import java.util.Optional;

import com.bso.companycob.application.dto.PaymentDTO;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContractPaymentReceiver {

    private final ContractRepository contractRepository;
    
    public void receivePayment(PaymentDTO paymentDto) {
        Optional<Contract> contractOpt = contractRepository.findById(paymentDto.getContractId());
        ContractNotFoundException.throwsWhen(contractOpt.isEmpty(), paymentDto.getContractId());
        contractOpt.get().receivePayment(paymentDto.getAmount());
    }
}
