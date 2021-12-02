package com.bso.companycob.domain.service.amount;

import java.util.EnumMap;
import java.util.Map;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.exception.DomainException;

public class AmountCalculatorDelegate {
    
    private final Map<CalcType, AmountCalculator> amountCalculators = new EnumMap<>(CalcType.class);

    public static final AmountCalculatorDelegate INSTANCE = new AmountCalculatorDelegate();

    private AmountCalculatorDelegate() {
        DefaultAmountCalculator defaultAmountCalculator = new DefaultAmountCalculator();
        amountCalculators.put(defaultAmountCalculator.getCalcType(), defaultAmountCalculator);
    }

    public AmountCalculator getAmountCalculator(CalcType calcType) {
        if (amountCalculators.containsKey(calcType)) return amountCalculators.get(calcType);

        throw new DomainException("CalcType not defined");
    }
}
