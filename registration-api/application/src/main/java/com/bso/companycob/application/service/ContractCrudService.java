package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.contract.ContractCreationDTO;
import com.bso.companycob.application.dto.quota.QuotaCreationDTO;
import com.bso.companycob.application.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.factory.contract.ContractFactory;
import com.bso.companycob.application.factory.quota.QuotaFactory;
import com.bso.companycob.application.lock.ContractLockeable;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class ContractCrudService {

    private final ContractFactory contractFactory;
    private final ContractRepository contractRepository;
    private final EventRaiser eventRaiser;
    private final QuotaFactory quotaFactory;
    private final LockManager lockManager;

    public Contract createContract(ContractCreationDTO dto) {
        var contractLockeable = new ContractLockeable(dto.getNumber());
        return lockManager.lockAndProcess(contractLockeable, () -> doCreate(dto));
    }

    public Contract doCreate(ContractCreationDTO dto) {
        Optional<Contract> contractOpt = contractRepository.findByNumber(dto.getNumber());
        ContractAlreadyExistsException.throwsWhen(contractOpt.isPresent(), dto.getNumber());

        List<Quota> quotas = createQuotas(dto.getQuotas());

        Contract contract = contractFactory.create(dto.getNumber(), dto.getDate(), dto.getBankId(), quotas, dto.getCalcType());
        contract = contractRepository.saveAndFlush(contract);
        eventRaiser.raise(new ContractCreatedEvent(contract));
        return contract;
    }

    private List<Quota> createQuotas(List<QuotaCreationDTO> dto) {
        List<Quota> quotas = new ArrayList<>(dto.size());
        dto.forEach(quotaCreationDTO -> quotas.add(quotaFactory.create(quotaCreationDTO)));
        return quotas;
    }

    public List<Contract> findAll() {
        return contractRepository.findAll();
    }
}
