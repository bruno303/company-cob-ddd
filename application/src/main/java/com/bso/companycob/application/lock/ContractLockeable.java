package com.bso.companycob.application.lock;

import com.bso.companycob.application.model.lock.Lockeable;
import com.bso.companycob.domain.entity.contract.Contract;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ContractLockeable implements Lockeable {

    private final Contract contract;

    @Override
    public String getLockKey() {
        return String.format("contract:%s", contract.getNumber());
    }
}
