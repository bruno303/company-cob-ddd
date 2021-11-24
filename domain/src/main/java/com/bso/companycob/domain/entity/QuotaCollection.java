package com.bso.companycob.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.DomainException;
import com.bso.companycob.domain.service.amount.AmountCalculator;

public class QuotaCollection implements Serializable {
    
    private final List<Quota> quotas;

    public QuotaCollection(List<Quota> quotas) {
        this.quotas = quotas;
        validate();
    }
    
    private void validate() {
        DomainException.throwsWhen(quotas == null || quotas.isEmpty(), "Quota collections must have quotas");
    }

    public List<Quota> getQuotas() {
        return Collections.unmodifiableList(quotas);
    }

    public Quota getNextOpenQuota() {
        return quotas
            .stream()
            .filter(q -> QuotaStatus.OPEN == q.getStatus())
            .findFirst()
            .orElseThrow(() -> new DomainException("No next open quota available"));
    }

    public void receivePayment(BigDecimal value) {

        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }
        
        Quota nextOpenQuota = this.getNextOpenQuota();
        BigDecimal quotaValue = nextOpenQuota.getAmount();
        if (value.compareTo(quotaValue) > 0) {
            nextOpenQuota.receive(quotaValue);
            value = value.add(quotaValue.negate());
            receivePayment(value);
            return;
        }

        nextOpenQuota.receive(value);
    }

    public void updateDebtAmount(AmountCalculator amountCalculator, BigDecimal interestRate) {
        quotas.forEach(q -> q.updateDebtAmount(amountCalculator, interestRate));
    }

    public void forEach(Consumer<? super Quota> consumer) {
        this.getQuotas().forEach(consumer);
    }

    public int size() {
        return this.getQuotas().size();
    }
}
