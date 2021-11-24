package com.bso.companycob.registration.dto;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ContractResponse {
    private final UUID id;
    private final String number;
    private final LocalDate date;
    private final List<QuotasResponse> quotas;
    private final BankResponse bank;
    private final CalcType calcType;

    @Data
    @Builder(toBuilder = true)
    public static class QuotasResponse {
        private final UUID id;
        private final int number;
        private final BigDecimal amount;
        private final BigDecimal updatedAmount;
        private final LocalDate date;
        private final QuotaStatus status;
        private final LocalDate dateUpdated;
    }
}
