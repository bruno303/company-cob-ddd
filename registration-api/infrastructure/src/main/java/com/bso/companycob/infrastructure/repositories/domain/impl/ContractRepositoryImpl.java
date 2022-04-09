package com.bso.companycob.infrastructure.repositories.domain.impl;

import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.companycob.domain.repositories.ContractRepository;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Quota;
import com.bso.companycob.infrastructure.mapper.ContractMapper;
import com.bso.companycob.infrastructure.repositories.PersistenceBankRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceContractRepository;
import com.bso.companycob.infrastructure.repositories.PersistenceQuotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ContractRepositoryImpl implements ContractRepository,
        ContractWriterRepository,
        ContractReaderRepository {

    private final PersistenceContractRepository contractRepository;
    private final PersistenceQuotaRepository persistenceQuotaRepository;
    private final PersistenceBankRepository persistenceBankRepository;
    private final ContractMapper contractMapper;

    @Override
    public Optional<Contract> findById(UUID id) {
        var contractOpt = contractRepository.findById(id);

        if (contractOpt.isEmpty()) {
            return Optional.empty();
        }

        var contract = contractOpt.get();
        loadQuotaAndBank(contract);

        return contractOpt.map(contractMapper::toDomainContract);

    }

    @Override
    public List<Contract> findAll() {
        var contracts = contractRepository.findAll();

        contracts.forEach(this::loadQuotaAndBank);

        return contracts.stream().map(contractMapper::toDomainContract)
                .collect(Collectors.toList());
    }

    @Override
    public Contract save(Contract entity) {
        var persistenceContract = contractMapper.toPersistenceContract(entity);
        var savedContract = contractRepository.save(persistenceContract);

        fulfillTransientInfos(persistenceContract, savedContract);
        saveQuotas(persistenceContract.getQuotas(), persistenceContract.getId());

        return contractMapper.toDomainContract(savedContract);
    }

    @Override
    public void delete(Contract entity) {
        var persistenceContract = contractMapper.toPersistenceContract(entity);
        contractRepository.delete(persistenceContract);
    }

    @Override
    public Contract saveAndFlush(Contract entity) {
        var persistenceContract = contractMapper.toPersistenceContract(entity);
        var savedContract = contractRepository.saveAndFlush(persistenceContract);

        fulfillTransientInfos(persistenceContract, savedContract);
        saveAndFlushQuotas(persistenceContract.getQuotas(), persistenceContract.getId());

        return contractMapper.toDomainContract(savedContract);
    }

    @Override
    public Optional<Contract> findByNumber(String number) {
        var contractPersistenceOpt = contractRepository.findByNumber(number);
        return contractPersistenceOpt.map(contractMapper::toDomainContract);
    }

    private void saveQuotas(List<Quota> quotas, UUID contractId) {
        saveQuotas(quotas, contractId, persistenceQuotaRepository::save);
    }

    private void saveAndFlushQuotas(List<Quota> quotas, UUID contractId) {
        saveQuotas(quotas, contractId, persistenceQuotaRepository::saveAndFlush);
    }

    private void saveQuotas(List<Quota> quotas, UUID contractId, UnaryOperator<Quota> saveFunction) {
        quotas.forEach(q -> {
            q.setContractId(contractId);
            saveFunction.apply(q);
        });
    }

    private Bank findBank(UUID bankId) {
        return persistenceBankRepository.findById(bankId).orElseThrow();
    }

    private List<Quota> findQuotas(UUID contractId) {
        return persistenceQuotaRepository.findByContractId(contractId);
    }

    private void loadQuotaAndBank(com.bso.companycob.infrastructure.entities.Contract contract) {
        Bank bank = findBank(contract.getBankId());
        contract.setBank(bank);
        contract.setQuotas(findQuotas(contract.getId()));
    }

    private void fulfillTransientInfos(com.bso.companycob.infrastructure.entities.Contract source,
                                       com.bso.companycob.infrastructure.entities.Contract target) {
        target.setQuotas(source.getQuotas());
        target.setBank(source.getBank());
    }
}
