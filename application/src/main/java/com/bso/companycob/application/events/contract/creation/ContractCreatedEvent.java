package com.bso.companycob.application.events.contract.creation;

import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.events.Event;

public class ContractCreatedEvent implements Event {
    private Contract contractCreated;

    public ContractCreatedEvent(Contract contract) {
        contractCreated = contract;
    }

    public Contract getContractCreated() {
        return contractCreated;
    }
}