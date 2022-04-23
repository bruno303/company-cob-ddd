package com.bso.companycob.application.bus.handler;

import com.bso.companycob.application.bus.response.ContractGetAllResponse;
import com.bso.companycob.application.bus.request.ContractGetAllRequest;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.repositories.ContractReaderRepository;
import com.bso.dracko.mediator.contract.RequestHandler;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ContractGetAllRequestHandler implements RequestHandler<ContractGetAllRequest, List<ContractGetAllResponse>> {

    private final ContractReaderRepository contractRepository;

    @Override
    public List<ContractGetAllResponse> handle(ContractGetAllRequest request) {
        var contracts = contractRepository.findAll();
        return toResponse(contracts);
    }

    private List<ContractGetAllResponse> toResponse(List<Contract> contracts) {
        return contracts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ContractGetAllResponse toResponse(Contract contract) {
        var quotaResponse = toQuotaResponse(contract);

        return ContractGetAllResponse.builder()
                .id(contract.getId())
                .calcType(contract.getCalcType())
                .date(contract.getDate())
                .number(contract.getNumber())
                .quotas(quotaResponse)
                .build();
    }

    private List<ContractGetAllResponse.QuotasResponse> toQuotaResponse(Contract contract) {
        List<Quota> quotas = contract.getQuotas().getQuotas();
        List<ContractGetAllResponse.QuotasResponse> quotasResponse = new ArrayList<>(quotas.size());
        quotas.forEach(q -> {
            var quotaResponse = ContractGetAllResponse.QuotasResponse.builder()
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
