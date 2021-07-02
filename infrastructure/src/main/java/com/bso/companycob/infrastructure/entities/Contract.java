package com.bso.companycob.infrastructure.entities;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bso.companycob.domain.entity.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;

@Entity
@Table(name = "CONTRACT")
public class Contract implements PersistenceEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;
    
    @Column(name = "NUMBER", length = 100, nullable = false)
    private String number;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @JoinColumn(name = "BANK_ID", nullable = false)
    @ManyToOne(optional = false, targetEntity = Bank.class)
    private Bank bank;

    @Column(name = "CALC_TYPE", nullable = false)
    private int calcType;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public int getCalcType() {
        return calcType;
    }

    public void setCalcType(int calcType) {
        this.calcType = calcType;
    }
    
    public com.bso.companycob.domain.entity.Contract toDomainContract() {
        com.bso.companycob.domain.entity.Bank domainBank = bank.toDomainBank();
        var quotas = new QuotaCollection(Collections.emptyList());
        return new com.bso.companycob.domain.entity.Contract(id, number, date, domainBank, quotas, CalcType.fromValue(calcType), null);
    }

    public static Contract fromDomainContract(com.bso.companycob.domain.entity.Contract contract) {
        var persistenceContract = new Contract();
        persistenceContract.setId(contract.getId());
        persistenceContract.setNumber(contract.getNumber());
        persistenceContract.setDate(contract.getDate());
        persistenceContract.setCalcType(contract.getCalcType().getValue());
        persistenceContract.setBank(Bank.fromDomainBank(contract.getBank()));
        
        return persistenceContract;
    }
}
