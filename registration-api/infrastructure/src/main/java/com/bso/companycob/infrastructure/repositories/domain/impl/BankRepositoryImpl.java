package com.bso.companycob.infrastructure.repositories.domain.impl;

import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import com.bso.companycob.infrastructure.mapper.BankMapper;
import com.bso.companycob.infrastructure.repositories.PersistenceBankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BankRepositoryImpl implements BankRepository {

    private final PersistenceBankRepository repository;
    private final BankMapper bankMapper;

    @Override
    public Optional<Bank> findById(UUID id) {
        var bankOpt = repository.findById(id);
        return bankOpt.map(bankMapper::toDomainBank);

    }

    @Override
    public List<Bank> findAll() {
        var allBanks = repository.findAll();
        return allBanks.stream().map(bankMapper::toDomainBank).collect(Collectors.toList());
    }

    @Override
    public Bank save(Bank entity) {
        var persistenceBank = bankMapper.toPersistenceBank(entity);
        var bankSaved = repository.save(persistenceBank);
        return bankMapper.toDomainBank(bankSaved);
    }

    @Override
    public void delete(Bank entity) {
        var persistenceBank = bankMapper.toPersistenceBank(entity);
        repository.delete(persistenceBank);        
    }

    @Override
    public Bank saveAndFlush(Bank entity) {
        var persistenceBank = bankMapper.toPersistenceBank(entity);
        var bankSaved = repository.saveAndFlush(persistenceBank);
        return bankMapper.toDomainBank(bankSaved);
    }
}