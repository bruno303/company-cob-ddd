package com.bso.companycob.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.utils.BigDecimalUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BankTest {
    
    @Test
    void testCreateBankWithSuccess() {
        UUID id = UUID.randomUUID();
        Bank bank = new Bank(id, "name", BigDecimal.TEN);
        Assertions.assertEquals(id, bank.getId());
        Assertions.assertEquals("name", bank.getName());
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, bank.getInterestRate()));
    }

    @Test
    void testCreateBankWithoutId() {
        Bank bank = new Bank(null, "name", BigDecimal.TEN);
        Assertions.assertNotNull(bank.getId());
        Assertions.assertEquals("name", bank.getName());
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, bank.getInterestRate()));
    }

    @Test
    void testCreateBankWithNameNull() {
        var id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Bank(id, null, BigDecimal.TEN));
    }

    @Test
    void testCreateBankWithNameEmpty() {
        var id = UUID.randomUUID();
        Assertions.assertThrows(DomainException.class, () -> new Bank(id, "", BigDecimal.TEN));
    }

    @Test
    void testCreateBankWithInterestRateNegative() {
        var id = UUID.randomUUID();
        final BigDecimal interestRate = BigDecimal.valueOf(-10);
        Assertions.assertThrows(DomainException.class, () -> new Bank(id, "name", interestRate));
    }
}
