package com.bso.companycob.domain.factory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.QuotaStatus;

public class QuotaFactory {

    private QuotaFactory() {}
    
    public static Quota createQuota() {
        return createQuota(1);
    }

    public static Quota createQuota(int number) {
        return createQuota(number, QuotaStatus.OPEN);
    }

    public static Quota createQuota(int number, QuotaStatus status) {
        return createQuota(number, status, LocalDate.now());
    }

    public static Quota createQuota(int number, QuotaStatus status, LocalDate date) {
        return createQuota(UUID.randomUUID(), number, status, date);
    }

    public static Quota createQuota(int number, QuotaStatus status, LocalDate date, LocalDate dateUpdated) {
        return createQuota(UUID.randomUUID(), number, status, date, dateUpdated);
    }

    public static Quota createQuota(UUID id, int number, QuotaStatus status, LocalDate date) {
        return createQuota(id, number, status, date, BigDecimal.TEN, BigDecimal.valueOf(20), LocalDate.now());
    }

    public static Quota createQuota(UUID id, int number, QuotaStatus status, LocalDate date, LocalDate dateUpdated) {
        return createQuota(id, number, status, date, BigDecimal.TEN, BigDecimal.valueOf(20), date);
    }

    public static Quota createQuota(UUID id, int number, QuotaStatus status, LocalDate date, BigDecimal amount, BigDecimal updatedAmount) {
        return createQuota(id, number, status, date, amount, updatedAmount, LocalDate.now());
    }

    public static Quota createQuota(UUID id, int number, QuotaStatus status, LocalDate date, BigDecimal amount, BigDecimal updatedAmount, LocalDate dateUpdated) {
        return new Quota(id, number, amount, updatedAmount, date, status, dateUpdated);
    }

    public static Quota createQuota(BigDecimal amount, BigDecimal updatedAmount) {
        return createQuota(UUID.randomUUID(), 1, QuotaStatus.OPEN, LocalDate.now(), amount, updatedAmount);
    }

    public static Quota createQuota(LocalDate date, LocalDate dateUpdated) {
        return createQuota(1, QuotaStatus.OPEN, date, dateUpdated);
    }

    public static Quota createQuota(UUID id) {
        return createQuota(id, 1, QuotaStatus.OPEN, LocalDate.now());
    }

}
