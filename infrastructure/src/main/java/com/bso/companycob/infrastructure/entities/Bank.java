package com.bso.companycob.infrastructure.entities;

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

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public com.bso.companycob.domain.entity.Bank toDomainBank() {
        return new com.bso.companycob.domain.entity.Bank(id, name, interestRate);
    }

    public static Bank fromDomainBank(com.bso.companycob.domain.entity.Bank bank) {
        var persistenceBank = new Bank();
        persistenceBank.setId(bank.getId());
        persistenceBank.setName(bank.getName());
        persistenceBank.setInterestRate(bank.getInterestRate());

        return persistenceBank;
    }
}
