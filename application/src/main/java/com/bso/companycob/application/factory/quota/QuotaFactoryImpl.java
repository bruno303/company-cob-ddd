package com.bso.companycob.application.factory.quota;

import com.bso.companycob.application.dto.quota.QuotaCreationDTO;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Component
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
    public Quota create(QuotaCreationDTO quotaCreationDTO) {
        return create(quotaCreationDTO.getNumber(), quotaCreationDTO.getAmount(),
                quotaCreationDTO.getDate(), quotaCreationDTO.getStatus());
    }
}
