package com.bso.companycob.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.service.amount.AmountCalculatorDelegate;

public class Contract implements Entity {

    private final UUID id;
    private final String number;
    private final LocalDate date;
    private final QuotaCollection quotas;
    private final Bank bank;
    private final CalcType calcType;
    private final transient AmountCalculatorDelegate amountCalculatorDelegate = new AmountCalculatorDelegate();

    public Contract(UUID id, String number, LocalDate date, Bank bank, QuotaCollection quotas, CalcType calcType) {
        this.id = id;
        this.number = number;
        this.date = date;
        this.bank = bank;
        this.quotas = quotas;
        this.calcType = calcType;
        validate();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public Bank getBank() {
        return bank;
    }

    public List<Quota> getQuotas() {
        return quotas.getQuotas();
    }

    private void validate() {
        //
    }

    public void receivePayment(BigDecimal value) {
        updateDebtAmount();
        quotas.receivePayment(value);
    }

    public void updateDebtAmount() {
        quotas.updateDebtAmount(amountCalculatorDelegate.getAmountCalculator(calcType), bank.getInterestRate());
    }
}
