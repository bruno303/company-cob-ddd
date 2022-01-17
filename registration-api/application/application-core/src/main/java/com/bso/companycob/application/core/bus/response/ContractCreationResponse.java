package com.bso.companycob.application.core.bus.response;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ContractCreationResponse {
    private final UUID id;
    private final String number;
    private final LocalDate date;
    private final List<QuotasResponse> quotas;
    private final CalcType calcType;

    @Data
    @Builder
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
