package com.bso.companycob.application.core.lock;

import com.bso.companycob.application.model.lock.Lockeable;
import com.bso.companycob.domain.entity.contract.Contract;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContractLockeable implements Lockeable {

    private final String number;

    public ContractLockeable(Contract contract) {
        this.number = contract.getNumber();
    }

    @Override
    public String getLockKey() {
        return String.format("contract:%s", this.number);
    }
}
