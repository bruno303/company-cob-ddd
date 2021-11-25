package com.bso.companycob.infrastructure.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bso.companycob.domain.entity.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CONTRACT")
@Getter
@Setter
public class Contract implements PersistenceEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;
    
    @Column(name = "NUMBER", length = 100, nullable = false)
    private String number;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @JoinColumn(name = "BANK_ID", nullable = false, referencedColumnName = "ID")
    @ManyToOne(optional = false, targetEntity = Bank.class)
    private Bank bank;

    @Column(name = "CALC_TYPE", nullable = false)
    private int calcType;

    @OneToMany(targetEntity = Quota.class, mappedBy = "contract", cascade = { CascadeType.ALL })
    private List<Quota> quotas;

    public com.bso.companycob.domain.entity.Contract toDomainContract() {
        com.bso.companycob.domain.entity.Bank domainBank = bank.toDomainBank();

        var quotas = new QuotaCollection(getQuotas().stream().map(Quota::toDomainQuota).collect(Collectors.toList()));
        return new com.bso.companycob.domain.entity.Contract(id, number, date, domainBank, quotas, CalcType.fromValue(calcType), null);
    }

    public static Contract fromDomainContract(com.bso.companycob.domain.entity.Contract contract) {
        var persistenceContract = new Contract();
        persistenceContract.setId(contract.getId());
        persistenceContract.setNumber(contract.getNumber());
        persistenceContract.setDate(contract.getDate());
        persistenceContract.setCalcType(contract.getCalcType().getValue());
        persistenceContract.setBank(Bank.fromDomainBank(contract.getBank()));

        List<Quota> quotas = convertQuotas(contract, persistenceContract);

        persistenceContract.setQuotas(quotas);
        
        return persistenceContract;
    }

    private static List<Quota> convertQuotas(com.bso.companycob.domain.entity.Contract contract, Contract persistenceContract) {
        List<Quota> quotas = new ArrayList<>(contract.getQuotas().size());
        contract.getQuotas().forEach(q -> {
            Quota quota = Quota.fromDomainQuota(q);
            quota.setContract(persistenceContract);
            quotas.add(quota);
        });
        return quotas;
    }
}
