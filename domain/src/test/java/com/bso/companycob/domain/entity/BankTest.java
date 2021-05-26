package com.bso.companycob.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import com.bso.companycob.domain.exception.DomainException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BankTest {
    
    @Test
    public void testCreateBankWithSuccess() {
        UUID id = UUID.randomUUID();
        Bank bank = new Bank(id, "name", BigDecimal.TEN);
        Assertions.assertEquals(id, bank.getId());
        Assertions.assertEquals("name", bank.getName());
        Assertions.assertTrue(BigDecimal.TEN.compareTo(bank.getInterestRate()) == 0);
    }

    @Test
    public void testCreateBankWithoutId() {
        Bank bank = new Bank(null, "name", BigDecimal.TEN);
        Assertions.assertNotNull(bank.getId());
        Assertions.assertEquals("name", bank.getName());
        Assertions.assertTrue(BigDecimal.TEN.compareTo(bank.getInterestRate()) == 0);
    }

    @Test
    public void testCreateBankWithNameNull() {
        Assertions.assertThrows(DomainException.class, () -> new Bank(UUID.randomUUID(), null, BigDecimal.TEN));
    }

    @Test
    public void testCreateBankWithNameEmpty() {
        Assertions.assertThrows(DomainException.class, () -> new Bank(UUID.randomUUID(), "", BigDecimal.TEN));
    }

    @Test
    public void testCreateBankWithInterestRateNegative() {
        Assertions.assertThrows(DomainException.class, () -> new Bank(UUID.randomUUID(), "name", BigDecimal.valueOf(-10)));
    }
}
