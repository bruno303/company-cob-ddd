package com.bso.companycob.domain.service.amount;

import java.util.HashMap;
import java.util.Map;

import com.bso.companycob.domain.enums.CalcType;

public class AmountCalculatorDelegate {
    
    private final Map<CalcType, AmountCalculator> amountCalculators = new HashMap<>();

    public AmountCalculatorDelegate() {
        DefaultAmountCalculator defaultAmountCalculator = new DefaultAmountCalculator();
        amountCalculators.put(defaultAmountCalculator.getCalcType(), defaultAmountCalculator);
    }

    public AmountCalculator getAmountCalculator(CalcType calcType) {
        if (amountCalculators.containsKey(calcType)) return amountCalculators.get(calcType);

        throw new RuntimeException("CalcType not defined");
    }
}
