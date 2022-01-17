package com.bso.companycob.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "CONTRACT")
@Getter
@Setter
public class Contract implements PersistenceEntity {

    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    private UUID id;
    
    @Column(name = "NUMBER", length = 100, nullable = false, unique = true)
    private String number;

    @Column(name = "DATE", nullable = false)
    private LocalDate date;

//    @JoinColumn(name = "BANK_ID", nullable = false, referencedColumnName = "ID")
//    @ManyToOne(optional = false, targetEntity = Bank.class)
    @Column(name = "BANK_ID", nullable = false)
    private UUID bankId;

    @Column(name = "CALC_TYPE", nullable = false)
    private int calcType;

//    @OneToMany(targetEntity = Quota.class, mappedBy = "contract", cascade = { CascadeType.ALL })
    @Transient
    private List<Quota> quotas;

    @Transient
    private Bank bank;
}
