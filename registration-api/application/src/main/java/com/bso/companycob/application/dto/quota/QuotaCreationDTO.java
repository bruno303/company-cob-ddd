package com.bso.companycob.application.dto.quota;

import com.bso.companycob.domain.enums.QuotaStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class QuotaCreationDTO {

    private final int number;
    private final BigDecimal amount;
    private final LocalDate date;
    private final QuotaStatus status;

}
