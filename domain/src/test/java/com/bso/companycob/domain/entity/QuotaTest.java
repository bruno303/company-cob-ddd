package com.bso.companycob.domain.entity;

import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.factory.QuotaFactory;
import com.bso.companycob.domain.service.amount.AmountCalculator;
import com.bso.companycob.domain.utils.BigDecimalUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class QuotaTest {

    @Test
    public void testCreateQuotaWithSuccess() {
        Quota quota = QuotaFactory.createQuota();
        Assertions.assertNotNull(quota.getId());
        Assertions.assertEquals(1, quota.getNumber());
        Assertions.assertEquals(BigDecimal.TEN, quota.getAmount());
        Assertions.assertEquals(BigDecimal.valueOf(20), quota.getUpdatedAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
        Assertions.assertEquals(LocalDate.now().atStartOfDay(), quota.getDate().atStartOfDay());
    }

    @Test
    public void testReceivePaymentSmallerThanValue() {
        Quota quota = QuotaFactory.createQuota();
        quota.receive(BigDecimal.valueOf(9));
        Assertions.assertEquals(BigDecimal.ONE, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
    }
    
    @Test
    public void testReceivePaymentEqualValue() {
        Quota quota = QuotaFactory.createQuota();
        quota.receive(BigDecimal.TEN);
        Assertions.assertEquals(BigDecimal.ZERO, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.PAID, quota.getStatus());
    }

    @Test
    public void testReceivePaymentGreaterThanValue() {
        Quota quota = QuotaFactory.createQuota();
        Assertions.assertThrows(RuntimeException.class, () -> quota.receive(BigDecimal.valueOf(11)));
        Assertions.assertEquals(BigDecimal.TEN, quota.getAmount());
        Assertions.assertEquals(QuotaStatus.OPEN, quota.getStatus());
    }

    @Test
    public void testGetDaysBetweenToday() {
        Quota quota = QuotaFactory.createQuota();
        Assertions.assertEquals(0, quota.getDaysFromDate());
    }

    @Test
    public void testGetDaysBetweenOldDate() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now().plusDays(-20), LocalDate.now());
        Assertions.assertEquals(20, quota.getDaysFromDate());
    }

    @Test
    public void testGetDaysBetweenFutureDate() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now().plusDays(34), LocalDate.now());
        Assertions.assertEquals(-34, quota.getDaysFromDate());
    }

    @Test
    public void testCreateQuotaWithIdNull() {
        Quota quota = QuotaFactory.createQuota((UUID)null);
        Assertions.assertNotNull(quota.getId());
    }

    @Test
    public void testCreateQuotaWithNumberZero() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(0));
    }

    @Test
    public void testCreateQuotaWithAmountNegative() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(BigDecimal.valueOf(-1), BigDecimal.TEN));
    }

    @Test
    public void testCreateQuotaWithAmountZero() {
        Quota quota = QuotaFactory.createQuota(BigDecimal.ZERO, BigDecimal.TEN);
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.ZERO, quota.getAmount()));
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, quota.getUpdatedAmount()));
    }

    @Test
    public void testCreateQuotaWithUpdatedAmountNegative() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(BigDecimal.TEN, BigDecimal.valueOf(-1)));
    }

    @Test
    public void testCreateQuotaWithUpdatedAmountZero() {
        Quota quota = QuotaFactory.createQuota(BigDecimal.TEN, BigDecimal.ZERO);
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.TEN, quota.getAmount()));
        Assertions.assertTrue(BigDecimalUtils.equals(BigDecimal.ZERO, quota.getUpdatedAmount()));
    }

    @Test
    public void testCreateQuotaWithDateNull() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota((LocalDate)null, LocalDate.now()));
    }

    @Test
    public void testCreateQuotaWithDateUpdatedNull() {
        Quota quota = QuotaFactory.createQuota(LocalDate.now(), (LocalDate)null);
        Assertions.assertEquals(LocalDate.now().atStartOfDay(), quota.getDateUpdated().atStartOfDay());
    }

    @Test
    public void testCreateQuotaWithStatusNull() {
        Assertions.assertThrows(DomainException.class, () -> QuotaFactory.createQuota(1, null));
    }

    @Test
    public void testIsNotUpdatedWhenDateUpdatedIsPast() {
        LocalDate date = LocalDate.now().plusDays(-1);
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertFalse(quota.isUpdated());
    }

    @Test
    public void testIsUpdatedWhenDateUpdatedIsToday() {
        LocalDate date = LocalDate.now();
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertTrue(quota.isUpdated());
    }

    @Test
    public void testIsUpdatedExceptionWhenDateUpdatedIsFuture() {
        LocalDate date = LocalDate.now().plusDays(1);
        Quota quota = QuotaFactory.createQuota(date, date);
        Assertions.assertThrows(DomainException.class, () -> quota.isUpdated());
    }

    @Test
    public void testUpdateDebtAmountWhenIsNotUpdated() {
        AmountCalculator amountCalculatorMock = Mockito.mock(AmountCalculator.class);
        BigDecimal interestRate = BigDecimal.valueOf(2.5);

        LocalDate date = LocalDate.now().plusDays(-1);
        Quota quota = QuotaFactory.createQuota(date, date);
        quota.updateDebtAmount(amountCalculatorMock, interestRate);

        Mockito.verify(amountCalculatorMock, times(1)).calculateUpdatedAmount(quota, 1L, interestRate);
    }

    @Test
    public void testUpdateDebtAmountWhenIsUpdated() {
        AmountCalculator amountCalculatorMock = Mockito.mock(AmountCalculator.class);
        BigDecimal interestRate = BigDecimal.valueOf(2.5);

        LocalDate date = LocalDate.now();
        Quota quota = QuotaFactory.createQuota(date, date);
        quota.updateDebtAmount(amountCalculatorMock, interestRate);

        Mockito.verify(amountCalculatorMock, times(0)).calculateUpdatedAmount(Mockito.any(), Mockito.anyLong(), Mockito.any());
    }
}
