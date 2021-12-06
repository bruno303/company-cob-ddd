package com.bso.companycob.infrastructure.repositories.jpa.impl;

import java.util.UUID;

import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.infrastructure.repositories.PersistenceContractRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistenceContractRepositoryJpaImpl extends JpaRepository<Contract, UUID>, PersistenceContractRepository {
    
}
