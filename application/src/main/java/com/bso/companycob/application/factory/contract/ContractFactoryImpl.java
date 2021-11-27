package com.bso.companycob.application.factory.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.entity.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;

import com.bso.companycob.domain.exception.BankNotFoundException;
import com.bso.companycob.domain.repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ContractFactoryImpl implements ContractFactory {

    private final BankRepository bankRepository;

    @Override
    public Contract create(UUID id, String number, LocalDate date, UUID bankId, List<Quota> quotas, CalcType calcType) {
        QuotaCollection quotaCollection = new QuotaCollection(quotas);

        Optional<Bank> bankOpt = bankRepository.findById(bankId);
        BankNotFoundException.throwsWhen(bankOpt.isEmpty(), bankId);

        return new Contract(id, number, date, bankOpt.get(), quotaCollection, calcType);
    }
    
    @Override
    public Contract create(String number, LocalDate date, UUID bankId, List<Quota> quotas, CalcType calcType) {
        QuotaCollection quotaCollection = new QuotaCollection(quotas);

        Optional<Bank> bankOpt = bankRepository.findById(bankId);
        BankNotFoundException.throwsWhen(bankOpt.isEmpty(), bankId);

        return new Contract(UUID.randomUUID(), number, date, bankOpt.get(), quotaCollection, calcType);
    }
}
