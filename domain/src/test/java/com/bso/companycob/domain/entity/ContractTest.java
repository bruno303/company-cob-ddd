package com.bso.companycob.domain.entity;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.service.amount.DefaultAmountCalculator;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ContractTest {

    @Test
    void testCallingReceivePayment() {
        BigDecimal interestRate = BigDecimal.valueOf(2.5);
        Bank bank = Mockito.mock(Bank.class);
        Mockito.when(bank.getInterestRate()).thenReturn(interestRate);

        QuotaCollection quotaCollection = Mockito.mock(QuotaCollection.class);
        Contract contract = new Contract(UUID.randomUUID(), "123456", LocalDate.now(), bank, quotaCollection, CalcType.DEFAULT);

        contract.receivePayment(BigDecimal.TEN);

        Mockito.verify(quotaCollection, times(1)).updateDebtAmount(Mockito.isA(DefaultAmountCalculator.class), Mockito.eq(interestRate));
        Mockito.verify(quotaCollection, times(1)).receivePayment(BigDecimal.TEN);
    }
    
    @Test
    void testCallingUpdateDebtAmount() {
        final BigDecimal interestRate = BigDecimal.valueOf(2.5);
        Bank bank = Mockito.mock(Bank.class);
        Mockito.when(bank.getInterestRate()).thenReturn(interestRate);

        QuotaCollection quotaCollection = Mockito.mock(QuotaCollection.class);
        Contract contract = new Contract(UUID.randomUUID(), "123456", LocalDate.now(), bank, quotaCollection, CalcType.DEFAULT);

        contract.updateDebtAmount();

        Mockito.verify(quotaCollection, times(1)).updateDebtAmount(Mockito.isA(DefaultAmountCalculator.class), Mockito.eq(interestRate));
    }
}
