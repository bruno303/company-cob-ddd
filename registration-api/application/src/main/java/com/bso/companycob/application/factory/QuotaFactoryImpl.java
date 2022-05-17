package com.bso.companycob.application.factory;

import com.bso.companycob.application.bus.request.ContractCreationRequest;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class QuotaFactoryImpl implements QuotaFactory {


    @Override
    public Quota create(UUID id, int number, BigDecimal amount, LocalDate date, QuotaStatus status) {
        return new Quota(id, number, amount, amount, date, status, date);
    }

    @Override
    public Quota create(int number, BigDecimal amount, LocalDate date, QuotaStatus status) {
        return new Quota(UUID.randomUUID(), number, amount, amount, date, status, date);
    }

    @Override
    public Quota create(ContractCreationRequest.QuotaData quotaData) {
        return create(quotaData.getNumber(), quotaData.getAmount(), quotaData.getDate(), quotaData.getStatus());
    }
}
