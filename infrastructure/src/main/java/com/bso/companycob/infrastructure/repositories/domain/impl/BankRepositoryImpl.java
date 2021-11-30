package com.bso.companycob.infrastructure.repositories.domain.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceBankRepository;

import org.springframework.stereotype.Component;

@Component
public class BankRepositoryImpl implements BankRepository {

    private final PersistenceBankRepository repository;

    public BankRepositoryImpl(PersistenceBankRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Bank> findById(UUID id) {
        var bankOpt = repository.findById(id);
        return bankOpt.map(com.bso.companycob.infrastructure.entities.Bank::toDomainBank);

    }

    @Override
    public List<Bank> findAll() {
        var allBanks = repository.findAll();
        return allBanks.stream().map(com.bso.companycob.infrastructure.entities.Bank::toDomainBank).collect(Collectors.toList());
    }

    @Override
    public Bank save(Bank entity) {
        var persistenceBank = com.bso.companycob.infrastructure.entities.Bank.fromDomainBank(entity);
        var bankSaved = repository.save(persistenceBank);
        return bankSaved.toDomainBank();
    }

    @Override
    public void delete(Bank entity) {
        var persistenceBank = com.bso.companycob.infrastructure.entities.Bank.fromDomainBank(entity);
        repository.delete(persistenceBank);        
    }

    @Override
    public Bank saveAndFlush(Bank entity) {
        var persistenceBank = com.bso.companycob.infrastructure.entities.Bank.fromDomainBank(entity);
        var bankSaved = repository.saveAndFlush(persistenceBank);
        return bankSaved.toDomainBank();
    }
}