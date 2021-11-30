package com.bso.companycob.domain.entity.bank;

import java.math.BigDecimal;
import java.util.UUID;

import com.bso.companycob.domain.entity.AbstractAggregateRoot;
import com.bso.companycob.domain.entity.AggregateRoot;
import com.bso.companycob.domain.entity.Entity;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.utils.BigDecimalUtils;

public class Bank extends AbstractAggregateRoot implements Entity, AggregateRoot {

    private UUID id;
    private String name;
    private BigDecimal interestRate;

    public Bank(UUID id, String name, BigDecimal interestRate) {
        this.id = id == null ? UUID.randomUUID() : id;
        this.name = name;
        this.interestRate = interestRate;
        validate();
    }

    private void validate() {
        DomainException.throwsWhen(id == null, "Bank id can't be null");
        DomainException.throwsWhen(name == null || name.length() == 0, "Bank name can't be null or empty");
        DomainException.throwsWhen(BigDecimalUtils.isNegative(interestRate), "Bank interestRate can't be negative");
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }
}
