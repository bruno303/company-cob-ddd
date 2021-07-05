package com.bso.companycob.application.factory.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.entity.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;

import org.springframework.stereotype.Component;

@Component
public class ContractFactoryImpl implements ContractFactory {

    private final EventRaiser eventRaiser;

    public ContractFactoryImpl(EventRaiser eventRaiser) {
        this.eventRaiser = eventRaiser;
    }

    @Override
    public Contract create(UUID id, String number, LocalDate date, Bank bank, List<Quota> quotas, CalcType calcType) {
        QuotaCollection quotaCollection = new QuotaCollection(quotas);

        return new Contract(id, number, date, bank, quotaCollection, calcType, eventRaiser);
    }
    
    @Override
    public Contract create(String number, LocalDate date, Bank bank, List<Quota> quotas, CalcType calcType) {
        QuotaCollection quotaCollection = new QuotaCollection(quotas);

        return new Contract(UUID.randomUUID(), number, date, bank, quotaCollection, calcType, eventRaiser);
    }
}
