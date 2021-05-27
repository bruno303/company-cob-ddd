package com.bso.companycob.domain.service.amount;

import java.math.BigDecimal;

import com.bso.companycob.domain.entity.Quota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class DefaultAmountCalculatorTest {
    
    private final DefaultAmountCalculator calculator = new DefaultAmountCalculator();

    @Test
    void testCalculate() {
        Quota quota = Mockito.mock(Quota.class);
        Mockito.when(quota.getUpdatedAmount()).thenReturn(BigDecimal.valueOf(30));
        Mockito.when(quota.getDaysFromDate()).thenReturn(50L);

        BigDecimal updatedAmount = calculator.calculateUpdatedAmount(quota, BigDecimal.valueOf(2.5));
        Assertions.assertEquals(0, BigDecimal.valueOf(67.5).compareTo(updatedAmount));
    }

}
