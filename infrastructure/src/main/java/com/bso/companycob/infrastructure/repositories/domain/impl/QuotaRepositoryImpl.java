package com.bso.companycob.infrastructure.repositories.domain.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.repositories.QuotaRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceQuotaRepository;

import org.springframework.stereotype.Component;

@Component
public class QuotaRepositoryImpl implements QuotaRepository {

    private final PersistenceQuotaRepository quotaRepository;

    public QuotaRepositoryImpl(PersistenceQuotaRepository quotaRepository) {
        this.quotaRepository = quotaRepository;
    }

    @Override
    public Optional<Quota> findById(UUID id) {
        var quotaOpt = quotaRepository.findById(id);
        if (!quotaOpt.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(quotaOpt.get().toDomainQuota());
    }

    @Override
    public List<Quota> findAll() {
        return quotaRepository
            .findAll()
            .stream()
            .map(com.bso.companycob.infrastructure.entities.Quota::toDomainQuota)
            .collect(Collectors.toList());
    }

    @Override
    public Quota save(Quota entity) {
        var quotaPersistence = com.bso.companycob.infrastructure.entities.Quota.fromDomainQuota(entity);
        var quotaSaved = quotaRepository.save(quotaPersistence);
        return quotaSaved.toDomainQuota();
    }

    @Override
    public void delete(Quota entity) {
        var quotaPersistence = com.bso.companycob.infrastructure.entities.Quota.fromDomainQuota(entity);
        quotaRepository.delete(quotaPersistence);
    }

    @Override
    public Quota saveAndFlush(Quota entity) {
        var quotaPersistence = com.bso.companycob.infrastructure.entities.Quota.fromDomainQuota(entity);
        var quotaSaved = quotaRepository.saveAndFlush(quotaPersistence);
        return quotaSaved.toDomainQuota();
    }
    
}
