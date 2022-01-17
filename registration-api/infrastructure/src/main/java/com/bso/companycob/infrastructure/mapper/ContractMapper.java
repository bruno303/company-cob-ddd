package com.bso.companycob.infrastructure.mapper;

import com.bso.companycob.domain.entity.contract.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.infrastructure.entities.Quota;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContractMapper {

    private final QuotaMapper quotaMapper;
    private final BankMapper bankMapper;

    public Contract toPersistenceContract(com.bso.companycob.domain.entity.contract.Contract contract) {
        var persistenceContract = new Contract();
        persistenceContract.setId(contract.getId());
        persistenceContract.setNumber(contract.getNumber());
        persistenceContract.setDate(contract.getDate());
        persistenceContract.setCalcType(contract.getCalcType().getValue());

        Bank bank = bankMapper.toPersistenceBank(contract.getBank());
        persistenceContract.setBank(bank);
        persistenceContract.setBankId(bank.getId());

        List<Quota> quotas = mapQuotas(contract, persistenceContract);

        persistenceContract.setQuotas(quotas);

        return persistenceContract;
    }

    public com.bso.companycob.domain.entity.contract.Contract toDomainContract(Contract contract) {
        com.bso.companycob.domain.entity.bank.Bank domainBank = bankMapper.toDomainBank(contract.getBank());

        var quotas = new QuotaCollection(contract.getQuotas().stream().map(quotaMapper::toDomainQuota).collect(Collectors.toList()));
        return new com.bso.companycob.domain.entity.contract.Contract(contract.getId(), contract.getNumber(),
                contract.getDate(), domainBank, quotas, CalcType.fromValue(contract.getCalcType()));
    }

    private List<Quota> mapQuotas(com.bso.companycob.domain.entity.contract.Contract contract, Contract persistenceContract) {
        List<Quota> quotas = new ArrayList<>(contract.getQuotas().size());
        contract.getQuotas().forEach(q -> {
            Quota quota = quotaMapper.toPersistenceQuota(q, persistenceContract.getId());
            quotas.add(quota);
        });
        return quotas;
    }

}
