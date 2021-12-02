package com.bso.companycob.domain.entity;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.factory.QuotaFactory;
import com.bso.companycob.domain.service.amount.AmountCalculator;
import com.bso.companycob.domain.utils.BigDecimalUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class QuotaTest {

    @Test
    void testCreateQuotaWithSuccess() {
        Quota quota = QuotaFactory.createQuota();
        Assertions.assertNotNull(quota.getId());
        Assertions.assertEquals(1, quota.getNumber());
        Assertions.assertEquals(BigDecimal.TEN, quota.getAmount());
        Assertions.assertEquals(BigDecimal.valueOf(20), quota.getUpdatedAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
        Assertions.assertEquals(LocalDate.now().atStartOfDay(), quota.getDate().atStartOfDay());
    }

    @Test
    void testReceivePaymentSmallerThanValue() {
        Quota quota = QuotaFactory.createQuota();
        quota.receive(BigDecimal.valueOf(9));
        Assertions.assertEquals(BigDecimal.ONE, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
    }
    
    @Test
    void testReceivePaymentEqualValue() {
        Quota quota = QuotaFactory.createQuota();
        quota.receive(BigDecimal.TEN);
        Assertions.assertEquals(BigDecimal.ZERO, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.PAID, quota.getStatus());
    }

    @Test
    void testReceivePaymentGreaterThanValue() {
        Quota quota = QuotaFactory.createQuota();
        final BigDecimal paymentAmount = BigDecimal.valueOf(11);
        Assertions.assertThrows(RuntimeException.class, () -> quota.receive(paymentAmount));
        Assertions.assertEquals(BigDecimal.TEN, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
    }

    @Test
    void testGetDaysBetweenToday() {
        Quota quota = QuotaFactory.createQuota();
        Assertions.assertEquals(0, quota.getDaysFromDate());
    }

    @Test
    void testGetDaysBetweenOldDate() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now().plusDays(-20), LocalDate.now());
        Assertions.assertEquals(20, quota.getDaysFromDate());
    }

    @Test
    void testGetDaysBetweenFutureDate() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now().plusDays(34), LocalDate.now());
        Assertions.assertEquals(-34, quota.getDaysFromDate());
    }

    @Test
    void testCreateQuotaWithIdNull() {
        Quota quota = QuotaFactory.createQuota((UUID)null);
        Assertions.assertNotNull(quota.getId());
    }

    @Test
    void testCreateQuotaWithNumberZero() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(0));
    }

    @Test
    void testCreateQuotaWithAmountNegative() {
        final BigDecimal amount = BigDecimal.valueOf(-1);
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(amount, BigDecimal.TEN));
    }

    @Test
    void testCreateQuotaWithAmountZero() {
        Quota quota = QuotaFactory.createQuota(BigDecimal.ZERO, BigDecimal.TEN);
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.ZERO, quota.getAmount()));
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, quota.getUpdatedAmount()));
    }

    @Test
    void testCreateQuotaWithUpdatedAmountNegative() {
        final BigDecimal updatedAmount = BigDecimal.valueOf(-1);
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(BigDecimal.TEN, updatedAmount));
    }

    @Test
    void testCreateQuotaWithUpdatedAmountZero() {
        Quota quota = QuotaFactory.createQuota(BigDecimal.TEN, BigDecimal.ZERO);
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, quota.getAmount()));
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.ZERO, quota.getUpdatedAmount()));
    }

    @Test
    void testCreateQuotaWithDateNull() {
        final LocalDate dateUpdated = LocalDate.now();
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(null, dateUpdated));
    }

    @Test
    void testCreateQuotaWithDateUpdatedNull() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now(), (LocalDate)null);
        Assertions.assertEquals(LocalDate.now().atStartOfDay(), quota.getDateUpdated().atStartOfDay());
    }

    @Test
    void testCreateQuotaWithStatusNull() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(1, null));
    }

    @Test
    void testIsNotUpdatedWhenDateUpdatedIsPast() {
        LocalDate date = LocalDate.now().plusDays(-1);
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertFalse(quota.isUpdated());
    }

    @Test
    void testIsUpdatedWhenDateUpdatedIsToday() {
        LocalDate date = LocalDate.now();
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertTrue(quota.isUpdated());
    }

    @Test
    void testIsUpdatedExceptionWhenDateUpdatedIsFuture() {
        LocalDate date = LocalDate.now().plusDays(1);
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertThrows(DomainException.class, quota::isUpdated);
    }

    @Test
    void testUpdateDebtAmountWhenIsNotUpdated() {
        AmountCalculator amountCalculatorMock = Mockito.mock(AmountCalculator.class);
        BigDecimal interestRate = BigDecimal.valueOf(2.5);

        LocalDate date = LocalDate.now().plusDays(-1);
        Quota quota = QuotaFactory.createQuota(date, date);
        quota.updateDebtAmount(amountCalculatorMock, interestRate);

        Mockito.verify(amountCalculatorMock, times(1)).calculateUpdatedAmount(quota, interestRate);
    }

    @Test
    void testUpdateDebtAmountWhenIsUpdated() {
        AmountCalculator amountCalculatorMock = Mockito.mock(AmountCalculator.class);
        BigDecimal interestRate = BigDecimal.valueOf(2.5);

        LocalDate date = LocalDate.now();
        Quota quota = QuotaFactory.createQuota(date, date);
        quota.updateDebtAmount(amountCalculatorMock, interestRate);

        Mockito.verify(amountCalculatorMock, times(0)).calculateUpdatedAmount(Mockito.any(), Mockito.any());
    }
}
