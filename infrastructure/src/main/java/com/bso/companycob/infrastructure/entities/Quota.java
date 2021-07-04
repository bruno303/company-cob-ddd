package com.bso.companycob.infrastructure.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bso.companycob.domain.enums.QuotaStatus;

@Entity
@Table(name = "QUOTA")
public class Quota implements PersistenceEntity {
    
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @Column(name = "AMOUNT", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "UPDATED_AMOUNT", nullable = false, precision = 12, scale = 2)
    private BigDecimal updatedAmount;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

    @Column(name = "STATUS", nullable = false)
    private int status;

    @Column(name = "DATE_UPDATED")
    private LocalDate dateUpdated;

    @ManyToOne(optional = false, targetEntity = Contract.class)
    @JoinColumn(name = "CONTRACT_ID", nullable = false, referencedColumnName = "ID")
    private Contract contract;

    @Override
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUpdatedAmount() {
        return updatedAmount;
    }

    public void setUpdatedAmount(BigDecimal updatedAmount) {
        this.updatedAmount = updatedAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(LocalDate dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
 
    public com.bso.companycob.domain.entity.Quota toDomainQuota() {
        return new com.bso.companycob.domain.entity.Quota(id, number, amount, updatedAmount, date, QuotaStatus.fromValue(status), dateUpdated);
    }

    public static Quota fromDomainQuota(com.bso.companycob.domain.entity.Quota quota) {
        var persistenceQuota = new Quota();
        persistenceQuota.setId(quota.getId());
        persistenceQuota.setNumber(quota.getNumber());
        persistenceQuota.setAmount(quota.getAmount());
        persistenceQuota.setUpdatedAmount(quota.getUpdatedAmount());
        persistenceQuota.setDate(quota.getDate());
        persistenceQuota.setStatus(quota.getStatus().getValue());
        persistenceQuota.setDateUpdated(quota.getDateUpdated());

        return persistenceQuota;
    }
}
