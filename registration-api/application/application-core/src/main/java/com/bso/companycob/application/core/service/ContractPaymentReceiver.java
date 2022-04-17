package com.bso.companycob.application.core.service;

import com.bso.companycob.application.model.dto.PaymentDTO;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

//@Service
@RequiredArgsConstructor
public class ContractPaymentReceiver {

    private final ContractRepository contractRepository;
    private final EventRaiser eventRaiser;
    
    public void receivePayment(PaymentDTO paymentDto) {
        Optional<Contract> contractOpt = contractRepository.findById(paymentDto.getContractId());
        ContractNotFoundException.throwsWhen(contractOpt.isEmpty(), paymentDto.getContractId());

        Contract contract = contractOpt.get();
        contract.receivePayment(paymentDto.getAmount());
        eventRaiser.raise(contract.getDomainEvents());
    }
}
