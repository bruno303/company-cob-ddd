package com.bso.companycob.domain.entity;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.service.amount.DefaultAmountCalculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContractTest {

    private static final String CONTRACT_NUMBER = "XPTO";
    private static final LocalDate CONTRACT_DATE = LocalDate.now();
    private static final Bank CONTRACT_BANK = Mockito.mock(Bank.class);
    private static final QuotaCollection CONTRACT_QUOTAS = Mockito.mock(QuotaCollection.class);
    private static final CalcType CONTRACT_CALC_TYPE = CalcType.DEFAULT;
    private static final EventRaiser EVENT_RAISER = Mockito.mock(EventRaiser.class);

    @Test
    void testCallingReceivePayment() {
        Mockito.reset(CONTRACT_BANK, CONTRACT_QUOTAS);

        BigDecimal interestRate = BigDecimal.valueOf(2.5);
        Mockito.when(CONTRACT_BANK.getInterestRate()).thenReturn(interestRate);

        Contract contract = createContract();
        contract.receivePayment(BigDecimal.TEN);

        Mockito.verify(CONTRACT_QUOTAS, times(1)).updateDebtAmount(Mockito.isA(DefaultAmountCalculator.class), Mockito.eq(interestRate));
        Mockito.verify(CONTRACT_QUOTAS, times(1)).receivePayment(BigDecimal.TEN);
    }
    
    @Test
    void testCallingUpdateDebtAmount() {
        Mockito.reset(CONTRACT_BANK, CONTRACT_QUOTAS);

        final BigDecimal interestRate = BigDecimal.valueOf(2.5);
        Mockito.when(CONTRACT_BANK.getInterestRate()).thenReturn(interestRate);

        Contract contract = createContract();
        contract.updateDebtAmount();

        Mockito.verify(CONTRACT_QUOTAS, times(1)).updateDebtAmount(Mockito.isA(DefaultAmountCalculator.class), Mockito.eq(interestRate));
    }

    @Test
    void testCreateContractWithSuccess() {
        UUID id = UUID.randomUUID();
        var contract = new Contract(id, CONTRACT_NUMBER, CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER);
        Assertions.assertEquals(id, contract.getId());
        Assertions.assertEquals(CONTRACT_NUMBER, contract.getNumber());
        Assertions.assertEquals(CONTRACT_DATE, contract.getDate());
        Assertions.assertEquals(CONTRACT_BANK, contract.getBank());
        Assertions.assertEquals(CONTRACT_QUOTAS, contract.getQuotas());
        Assertions.assertEquals(CONTRACT_CALC_TYPE, contract.getCalcType());
    }

    @Test
    void testCreateContractWithIdNullWillCreateUUID() {
        var contract = new Contract(null, CONTRACT_NUMBER, CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER);
        Assertions.assertNotNull(contract.getId());
    }

    @Test
    void testCreateContractWithNumberNull() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, null, CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER));
    }

    @Test
    void testCreateContractWithNumberEmpty() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, "", CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER));
    }

    @Test
    void testCreateContractWithDateNull() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, CONTRACT_NUMBER, null, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER));
    }

    @Test
    void testCreateContractWithBankNull() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, CONTRACT_NUMBER, CONTRACT_DATE, null, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER));
    }

    @Test
    void testCreateContractWithQuotasNull() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, CONTRACT_NUMBER, CONTRACT_DATE, CONTRACT_BANK, null, CONTRACT_CALC_TYPE, EVENT_RAISER));
    }

    @Test
    void testCreateContractWithCalcTypeNull() {
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Contract(id, CONTRACT_NUMBER, CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, null, EVENT_RAISER));
    }

    private Contract createContract() {
        return new Contract(UUID.randomUUID(), CONTRACT_NUMBER, CONTRACT_DATE, CONTRACT_BANK, CONTRACT_QUOTAS, CONTRACT_CALC_TYPE, EVENT_RAISER);
    }
}
