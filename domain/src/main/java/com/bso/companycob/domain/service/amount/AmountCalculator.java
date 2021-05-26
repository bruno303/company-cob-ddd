package com.bso.companycob.domain.service.amount;

import java.math.BigDecimal;

import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.enums.CalcType;

public interface AmountCalculator {
    
    BigDecimal calculateUpdatedAmount(Quota quota, long daysFromDate, BigDecimal interestRate);
    CalcType getCalcType();

}
