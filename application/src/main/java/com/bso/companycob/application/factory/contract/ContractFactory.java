package com.bso.companycob.application.factory.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.enums.CalcType;

public interface ContractFactory {
    
    Contract create(UUID id, String number, LocalDate date, Bank bank, List<Quota> quotas, CalcType calcType);
    Contract create(String number, LocalDate date, Bank bank, List<Quota> quotas, CalcType calcType);

}
