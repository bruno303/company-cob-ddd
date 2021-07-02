package com.bso.companycob.infrastructure.repositories.domain.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.repositories.ContractRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceContractRepository;

import org.springframework.stereotype.Component;

@Component
public class ContractRepositoryImpl implements ContractRepository {

    private final PersistenceContractRepository contractRepository;

    public ContractRepositoryImpl(PersistenceContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    @Override
    public Optional<Contract> findById(UUID id) {
        var contractOpt = contractRepository.findById(id);

        if (!contractOpt.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(contractOpt.get().toDomainContract());
    }

    @Override
    public List<Contract> findAll() {
        return contractRepository.findAll()
            .stream()
            .map(com.bso.companycob.infrastructure.entities.Contract::toDomainContract)
            .collect(Collectors.toList());
    }

    @Override
    public Contract save(Contract entity) {
        var persistenceContract = com.bso.companycob.infrastructure.entities.Contract.fromDomainContract(entity);
        var savedContract = contractRepository.save(persistenceContract);
        return savedContract.toDomainContract();
    }

    @Override
    public void delete(Contract entity) {
        var persistenceContract = com.bso.companycob.infrastructure.entities.Contract.fromDomainContract(entity);
        contractRepository.delete(persistenceContract);
    }
    
}
