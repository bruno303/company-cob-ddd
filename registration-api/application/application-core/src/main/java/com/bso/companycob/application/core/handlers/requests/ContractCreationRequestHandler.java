package com.bso.companycob.application.core.handlers.requests;

import com.bso.companycob.application.core.bus.request.ContractCreationRequest;
import com.bso.companycob.application.core.bus.response.ContractCreationResponse;
import com.bso.companycob.application.core.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.core.factory.contract.ContractFactory;
import com.bso.companycob.application.core.factory.quota.QuotaFactory;
import com.bso.companycob.application.core.lock.ContractLockeable;
import com.bso.companycob.application.model.lock.LockManager;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.entity.contract.QuotaCollection;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.exception.ContractAlreadyExistsException;
import com.bso.companycob.domain.repositories.ContractWriterRepository;
import com.bso.dracko.mediator.contract.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ContractCreationRequestHandler implements RequestHandler<ContractCreationRequest, ContractCreationResponse> {

    private final ContractFactory contractFactory;
    private final ContractWriterRepository contractRepository;
    private final EventRaiser eventRaiser;
    private final QuotaFactory quotaFactory;
    private final LockManager lockManager;

    @Override
    public ContractCreationResponse handle(ContractCreationRequest command) {
        var contractLockeable = new ContractLockeable(command.getNumber());
        return lockManager.lockAndProcess(contractLockeable, () -> doCreate(command));
    }

    public ContractCreationResponse doCreate(ContractCreationRequest command) {
        Optional<Contract> contractOpt = contractRepository.findByNumber(command.getNumber());
        ContractAlreadyExistsException.throwsWhen(contractOpt.isPresent(), command.getNumber());

        List<Quota> quotas = createQuotas(command.getQuotas());

        Contract contract = contractFactory.create(command.getNumber(), command.getDate(), command.getBankId(), quotas, command.getCalcType());
        contract = contractRepository.saveAndFlush(contract);
        eventRaiser.raise(new ContractCreatedEvent(contract));
        return toResponse(contract);
    }

    private List<Quota> createQuotas(List<ContractCreationRequest.QuotaData> dto) {
        List<Quota> quotas = new ArrayList<>(dto.size());
        dto.forEach(quotaCreationDTO -> quotas.add(quotaFactory.create(quotaCreationDTO)));
        return quotas;
    }

    private ContractCreationResponse toResponse(Contract contract) {
        var quotas = toQuotas(contract.getQuotas());

        return ContractCreationResponse.builder()
                .calcType(contract.getCalcType())
                .id(contract.getId())
                .date(contract.getDate())
                .number(contract.getNumber())
                .quotas(quotas)
                .build();
    }

    private List<ContractCreationResponse.QuotasResponse> toQuotas(QuotaCollection quotas) {
        if (quotas == null) {
            return Collections.emptyList();
        }

        List<ContractCreationResponse.QuotasResponse> quotasResponse = new ArrayList<>(quotas.size());
        quotas.forEach(q -> {
            var quotaResponse = ContractCreationResponse.QuotasResponse.builder()
                    .amount(q.getAmount())
                    .id(q.getId())
                    .dateUpdated(q.getDateUpdated())
                    .date(q.getDate())
                    .number(q.getNumber())
                    .status(q.getStatus())
                    .updatedAmount(q.getUpdatedAmount())
                    .build();
            quotasResponse.add(quotaResponse);
        });
        return quotasResponse;
    }
}
