package com.bso.companycob.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.service.amount.AmountCalculator;
import com.bso.companycob.domain.utils.BigDecimalUtils;

public class Quota implements Entity {
    
    private UUID id;
    private int number;
    private BigDecimal amount;
    private BigDecimal updatedAmount;
    private LocalDate date;
    private QuotaStatus status;
    private LocalDate dateUpdated;

    public Quota(UUID id, int number, BigDecimal amount, BigDecimal updatedAmount, LocalDate date, QuotaStatus quotaStatus, LocalDate dateUpdated) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.number = number;
        this.amount = amount;
        this.updatedAmount = updatedAmount;
        this.date = date;
        this.status = quotaStatus;
        this.dateUpdated = dateUpdated == null ? LocalDate.now() : dateUpdated;
        validate();
    }

    private void validate() {
        DomainException.throwsWhen(id == null, "Quota id can't be null");
        DomainException.throwsWhen(number <= 0, "Quota number must be greater than 0");
        DomainException.throwsWhen(BigDecimalUtils.isNegative(amount), "Quota amount can't be negative");
        DomainException.throwsWhen(BigDecimalUtils.isNegative(updatedAmount), "Quota updatedAmount can't be negative");
        DomainException.throwsWhen(date == null, "Quota date can't be null");
        DomainException.throwsWhen(status == null, "Quota status can't be null");
    }

    @Override
    public UUID getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public QuotaStatus getStatus() {
        return status;
    }

    public BigDecimal getUpdatedAmount() {
        return updatedAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void receive(BigDecimal value) {
        DomainException.throwsWhen(BigDecimalUtils.firstIsGreaterThanSecond(value, this.amount), "Amount receiving is greater than the quota value");

        this.amount = this.amount.add(value.negate());
        if (BigDecimalUtils.equals(this.amount, BigDecimal.ZERO)) {
            this.status = QuotaStatus.PAID;
        }
    }

    public void updateDebtAmount(AmountCalculator amountCalculator, BigDecimal interestRate) {
        if (!isUpdated()) {
            doUpdateDebtAmount(amountCalculator, interestRate);
        }
    }

    private void doUpdateDebtAmount(AmountCalculator amountCalculator, BigDecimal interestRate) {
        this.updatedAmount = amountCalculator.calculateUpdatedAmount(this, interestRate);
        this.dateUpdated = LocalDate.now();
    }

    public long getDaysFromDate() {
        return ChronoUnit.DAYS.between(date, LocalDate.now());
    }

    public boolean isUpdated() {
        long days = ChronoUnit.DAYS.between(dateUpdated, LocalDate.now());
        DomainException.throwsWhen(days < 0, "DateUpdated can't be future");
        return days == 0;
    }
}
