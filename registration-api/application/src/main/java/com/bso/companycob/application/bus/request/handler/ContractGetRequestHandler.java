package com.bso.companycob.application.bus.request.handler;

import com.bso.companycob.application.bus.request.ContractGetRequest;
import com.bso.companycob.application.bus.response.ContractGetResponse;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.dracko.mediator.contract.RequestHandler;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ContractGetRequestHandler implements RequestHandler<ContractGetRequest, Optional<ContractGetResponse>> {

    private final ContractReaderRepository contractRepository;

    @Override
    public Optional<ContractGetResponse> handle(ContractGetRequest request) {
        var contractOpt = contractRepository.findById(request.getId());
        return contractOpt.map(this::toResponse);
    }

    private ContractGetResponse toResponse(Contract contract) {
        var quotaResponse = toQuotaResponse(contract);

        return ContractGetResponse.builder()
                .id(contract.getId())
                .calcType(contract.getCalcType())
                .date(contract.getDate())
                .number(contract.getNumber())
                .quotas(quotaResponse)
                .build();
    }

    private List<ContractGetResponse.QuotasResponse> toQuotaResponse(Contract contract) {
        List<Quota> quotas = contract.getQuotas().getQuotas();
        List<ContractGetResponse.QuotasResponse> quotasResponse = new ArrayList<>(quotas.size());
        quotas.forEach(q -> {
            var quotaResponse = ContractGetResponse.QuotasResponse.builder()
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
