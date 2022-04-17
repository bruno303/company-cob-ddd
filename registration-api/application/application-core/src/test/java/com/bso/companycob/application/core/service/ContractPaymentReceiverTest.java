package com.bso.companycob.application.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.application.core.service.ContractPaymentReceiver;
import com.bso.companycob.application.model.dto.PaymentDTO;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractNotFoundException;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContractPaymentReceiverTest {

    private final ContractRepository contractRepository = Mockito.mock(ContractRepository.class);
    private final EventRaiser eventRaiser = Mockito.mock(EventRaiser.class);
    private ContractPaymentReceiver contractPaymentReceiver;

    @BeforeEach
    public void init() {
        contractPaymentReceiver = new ContractPaymentReceiver(contractRepository, eventRaiser);
    }

    @Test
    void testCallUpdateDebtWhenContractIsPresent() {
        PaymentDTO dto = new PaymentDTO(UUID.randomUUID(), BigDecimal.TEN);
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.of(contract));

        contractPaymentReceiver.receivePayment(dto);

        Mockito.verify(contractRepository, Mockito.times(1)).findById(dto.getContractId());
        Mockito.verify(contract, Mockito.times(1)).receivePayment(dto.getAmount());
    }

    @Test
    void testCallUpdateDebtWhenContractIsNotPresent() {
        PaymentDTO dto = new PaymentDTO(UUID.randomUUID(), BigDecimal.TEN);
        Contract contract = createContractMock();

        Mockito.when(contractRepository.findById(dto.getContractId())).thenReturn(Optional.empty());

        var exception = assertThrows(ContractNotFoundException.class,
                () -> contractPaymentReceiver.receivePayment(dto));

        Assertions.assertThat(exception.getContractId()).isEqualByComparingTo(dto.getContractId());

        Mockito.verify(contractRepository, Mockito.times(1)).findById(dto.getContractId());
        Mockito.verify(contract, Mockito.times(0)).updateDebtAmount();
    }

    private Contract createContractMock() {
        return Mockito.mock(Contract.class);
    }
    
}
