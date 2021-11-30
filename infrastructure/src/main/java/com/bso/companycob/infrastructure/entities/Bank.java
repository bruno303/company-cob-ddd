package com.bso.companycob.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "BANK")
@Getter
@Setter
public class Bank implements PersistenceEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "NAME", length = 300, nullable = false)
    private String name;

    @Column(name = "INTEREST_RATE", nullable = false, precision = 12, scale = 2)
    private BigDecimal interestRate;

    @OneToMany(targetEntity = Contract.class, mappedBy = "bank", cascade = { CascadeType.ALL })
    private List<Contract> contracts;

    public com.bso.companycob.domain.entity.bank.Bank toDomainBank() {
        return new com.bso.companycob.domain.entity.bank.Bank(id, name, interestRate);
    }

    public static Bank fromDomainBank(com.bso.companycob.domain.entity.bank.Bank bank) {
        var persistenceBank = new Bank();
        persistenceBank.setId(bank.getId());
        persistenceBank.setName(bank.getName());
        persistenceBank.setInterestRate(bank.getInterestRate());

        return persistenceBank;
    }
}
