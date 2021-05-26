package com.bso.companycob.domain.service.amount;

import java.math.BigDecimal;

import com.bso.companycob.domain.entity.Quota;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DefaultAmountCalculatorTest {
    
    private final DefaultAmountCalculator calculator = new DefaultAmountCalculator();

    @Test
    public void testCalculate() {
        Quota quota = Mockito.mock(Quota.class);
        Mockito.when(quota.getUpdatedAmount()).thenReturn(BigDecimal.valueOf(30));

        BigDecimal updatedAmount = calculator.calculateUpdatedAmount(quota, 50, BigDecimal.valueOf(2.5));
        Assertions.assertTrue(BigDecimal.valueOf(67.5).compareTo(updatedAmount) == 0);
    }

}
