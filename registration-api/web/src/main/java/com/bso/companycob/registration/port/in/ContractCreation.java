package com.bso.companycob.registration.port.in;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ContractCreation {
    private String number;
    private LocalDate date;
    private UUID bankId;
    private List<ContractCreation.Quota> quotas;
    private CalcType calcType;

    @Data
    @NoArgsConstructor
    public static class Quota {
        private int number;
        private BigDecimal amount;
        private LocalDate date;
        private QuotaStatus status;
    }
}
