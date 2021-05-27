package com.bso.application.service;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.bso.application.dto.PaymentDTO;
import com.bso.application.repository.ContractRepository;
import com.bso.companycob.domain.entity.Contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContractPaymentReceiverTest {

    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private ContractPaymentReceiver contractPaymentReceiver;

    @BeforeEach
    public void init() {
        contractPaymentReceiver = new ContractPaymentReceiver(contractRepository);
    }

    @Test
    void testCallUpdateDebtWhenContractIsPresent() {
        PaymentDTO dto = new PaymentDTO(UUID.randomUUID(), BigDecimal.TEN);
        Contract contract = createContract();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractPaymentReceiver.receivePayment(dto);

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(1)).receivePayment(dto.getAmount());
    }

    @Test
    void testCallUpdateDebtWhenContractIsNotPresent() {
        PaymentDTO dto = new PaymentDTO(UUID.randomUUID(), BigDecimal.TEN);
        Contract contract = createContract();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.empty());

        contractPaymentReceiver.receivePayment(dto);

        Mockito.verify(contractRepository, times(1)).findById(dto.getContractId());
        Mockito.verify(contract, times(0)).updateDebtAmount();
    }

    private Contract createContract() {
        return Mockito.mock(Contract.class);
    }
    
}
