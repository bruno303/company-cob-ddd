package com.bso.companycob.infrastructure.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.infrastructure.entities.PersistenceEntity;

public interface PersistenceRepository<T extends PersistenceEntity> {
    Optional<T> findById(UUID id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
}
