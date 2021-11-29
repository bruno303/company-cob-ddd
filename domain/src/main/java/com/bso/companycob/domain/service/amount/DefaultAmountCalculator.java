package com.bso.companycob.domain.service.amount;

import java.math.BigDecimal;

import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.CalcType;

public class DefaultAmountCalculator implements AmountCalculator {

    @Override
    public CalcType getCalcType() {
        return CalcType.DEFAULT;
    }

    @Override
    public BigDecimal calculateUpdatedAmount(Quota quota, BigDecimal interestRate) {
        BigDecimal rate = BigDecimal.valueOf(quota.getDaysFromDate()).multiply(interestRate);
        BigDecimal interestAmount = quota.getUpdatedAmount().multiply(rate).divide(BigDecimal.valueOf(100));
        return quota.getUpdatedAmount().add(interestAmount);
    }
    
}