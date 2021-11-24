package com.bso.companycob.application.dto.contract;

import com.bso.companycob.application.dto.quota.QuotaCreationDTO;
import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.enums.CalcType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ContractCreationDTO {
    private final String number;
    private final LocalDate date;
    private final UUID bankId;
    private final List<QuotaCreationDTO> quotas;
    private final CalcType calcType;
}
