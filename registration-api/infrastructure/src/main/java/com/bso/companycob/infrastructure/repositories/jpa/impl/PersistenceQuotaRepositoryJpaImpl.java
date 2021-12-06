package com.bso.companycob.infrastructure.repositories.jpa.impl;

import java.util.UUID;

import com.bso.companycob.infrastructure.entities.Quota;
import com.bso.companycob.infrastructure.repositories.PersistenceQuotaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersistenceQuotaRepositoryJpaImpl extends JpaRepository<Quota, UUID>, PersistenceQuotaRepository {
    
}
