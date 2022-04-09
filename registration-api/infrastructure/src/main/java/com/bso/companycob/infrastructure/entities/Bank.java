package com.bso.companycob.infrastructure.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

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
}
