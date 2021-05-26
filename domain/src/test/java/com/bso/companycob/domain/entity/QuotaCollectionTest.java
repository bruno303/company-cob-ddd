package com.bso.companycob.domain.entity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.factory.QuotaFactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class QuotaCollectionTest {

    @Test
    public void testCreateQuotaCollectionWithSuccess() {
        Quota quota = QuotaFactory.createQuota();
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota));
        Assertions.assertEquals(quota, quotaCollection.getQuotas().get(0));
    }

    @Test
    public void testCreateQuotaCollectionWithNullQuotas() {
        Assertions.assertThrows(DomainException.class, () -> new QuotaCollection(null));
    }

    @Test
    public void testCreateQuotaCollectionWithEmptyQuotas() {
        Assertions.assertThrows(DomainException.class, () -> new QuotaCollection(Collections.emptyList()));
    }
    
    @Test
    public void testGetNextOpenQuotaWhenIsFirst() {
        Quota quota = QuotaFactory.createQuota();
        Quota quota2 = QuotaFactory.createQuota(2, QuotaStatus.PAID);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2));

        Quota nextOpenQuota = quotaCollection.getNextOpenQuota();
        Assertions.assertEquals(QuotaStatus.OPEN, nextOpenQuota.getStatus());
        Assertions.assertEquals(1, nextOpenQuota.getNumber());
    }

    @Test
    public void testGetNextOpenQuotaWhenIsMiddle() {
        Quota quota = QuotaFactory.createQuota(1, QuotaStatus.PAID);
        Quota quota2 = QuotaFactory.createQuota(2);
        Quota quota3 = QuotaFactory.createQuota(3, QuotaStatus.PAID);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2, quota3));

        Quota nextOpenQuota = quotaCollection.getNextOpenQuota();
        Assertions.assertEquals(QuotaStatus.OPEN, nextOpenQuota.getStatus());
        Assertions.assertEquals(2, nextOpenQuota.getNumber());
    }

    @Test
    public void testGetNextOpenQuotaWhenIsLast() {
        Quota quota = QuotaFactory.createQuota(1, QuotaStatus.PAID);
        Quota quota2 = QuotaFactory.createQuota(2);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2));

        Quota nextOpenQuota = quotaCollection.getNextOpenQuota();
        Assertions.assertEquals(QuotaStatus.OPEN, nextOpenQuota.getStatus());
        Assertions.assertEquals(2, nextOpenQuota.getNumber());
    }

    @Test
    public void testGetNextOpenQuotaWhenHaveNone() {
        Quota quota = QuotaFactory.createQuota(1, QuotaStatus.PAID);
        Quota quota2 = QuotaFactory.createQuota(2, QuotaStatus.PAID);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2));

        Assertions.assertThrows(DomainException.class, () -> quotaCollection.getNextOpenQuota());
    }

    @Test
    public void testReceivePaymentZero() {
        Quota quota = QuotaFactory.createQuota();
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota));

        quotaCollection.receivePayment(BigDecimal.ZERO);
        
        Quota quotaAssert = quotaCollection.getQuotas().get(0);
        Assertions.assertEquals(QuotaStatus.OPEN, quotaAssert.getStatus());
        Assertions.assertEquals(BigDecimal.TEN, quotaAssert.getAmount());
    }

    @Test
    public void testReceivePaymentSmallerThanValue() {
        Quota quota = QuotaFactory.createQuota();
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota));

        quotaCollection.receivePayment(BigDecimal.valueOf(9));
        
        Quota quotaAssert = quotaCollection.getQuotas().get(0);
        Assertions.assertEquals(QuotaStatus.OPEN, quotaAssert.getStatus());
        Assertions.assertEquals(BigDecimal.ONE, quotaAssert.getAmount());
    }

    @Test
    public void testReceivePaymentEqualsValue() {
        Quota quota = QuotaFactory.createQuota();
        Quota quota2 = QuotaFactory.createQuota(2);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2));

        quotaCollection.receivePayment(BigDecimal.TEN);
        
        Assertions.assertEquals(QuotaStatus.PAID, quotaCollection.getQuotas().get(0).getStatus());
        Assertions.assertEquals(BigDecimal.ZERO, quotaCollection.getQuotas().get(0).getAmount());

        Assertions.assertEquals(QuotaStatus.OPEN, quotaCollection.getQuotas().get(1).getStatus());
        Assertions.assertEquals(BigDecimal.TEN, quotaCollection.getQuotas().get(1).getAmount());
    }

    @Test
    public void testReceivePaymentGreaterThanValue() {
        Quota quota = QuotaFactory.createQuota();
        Quota quota2 = QuotaFactory.createQuota(2);
        Quota quota3 = QuotaFactory.createQuota(3);
        QuotaCollection quotaCollection = new QuotaCollection(List.of(quota, quota2, quota3));

        quotaCollection.receivePayment(BigDecimal.valueOf(25));
        
        Assertions.assertEquals(QuotaStatus.PAID, quotaCollection.getQuotas().get(0).getStatus());
        Assertions.assertEquals(BigDecimal.ZERO, quotaCollection.getQuotas().get(0).getAmount());

        Assertions.assertEquals(QuotaStatus.PAID, quotaCollection.getQuotas().get(1).getStatus());
        Assertions.assertEquals(BigDecimal.ZERO, quotaCollection.getQuotas().get(1).getAmount());

        Assertions.assertEquals(QuotaStatus.OPEN, quotaCollection.getQuotas().get(2).getStatus());
        Assertions.assertEquals(BigDecimal.valueOf(5), quotaCollection.getQuotas().get(2).getAmount());
    }
}
