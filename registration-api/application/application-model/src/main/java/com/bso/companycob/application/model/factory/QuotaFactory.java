package com.bso.companycob.application.model.factory;

import com.bso.companycob.application.model.bus.request.ContractCreationRequest;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.QuotaStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public interface QuotaFactory {
    
    Quota create(UUID id, int number, BigDecimal amount, LocalDate date, QuotaStatus status);
    Quota create(int number, BigDecimal amount, LocalDate date, QuotaStatus status);
    Quota create(ContractCreationRequest.QuotaData quotaData);

}
