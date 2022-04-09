package com.bso.companycob.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "QUOTA")
@Getter
@Setter
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

//    @ManyToOne(optional = false, targetEntity = Contract.class)
//    @JoinColumn(name = "CONTRACT_ID", nullable = false, referencedColumnName = "ID")
    @Column(name = "CONTRACT_ID", nullable = false)
    private UUID contractId;
}
