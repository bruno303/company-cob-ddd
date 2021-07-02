package com.bso.companycob.infrastructure.repositories.jpa.impl;

import java.util.UUID;

import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.repositories.PersistenceBankRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistenceBankRepositoryJpaImpl extends JpaRepository<Bank, UUID>, PersistenceBankRepository {
    
}
