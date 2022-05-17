package com.bso.companycob.registration.controller;

import com.bso.companycob.application.bus.request.ContractCreationRequest;
import com.bso.companycob.application.bus.response.ContractCreationResponse;
import com.bso.companycob.registration.config.CompanyCobController;
import com.bso.companycob.registration.port.in.ContractCreation;
import com.bso.dracko.mediator.contract.Mediator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.stream.Collectors;

@CompanyCobController
@RequiredArgsConstructor
public class ContractWriteController {

    private final Mediator mediator;

    @PostMapping("v1/contract")
    public ResponseEntity<ContractCreationResponse> createContract(@RequestBody ContractCreation contractCreation) {
        ContractCreationRequest cmd = toRequestContract(contractCreation);
        var response = mediator.dispatch(cmd);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private ContractCreationRequest toRequestContract(ContractCreation contract) {
        var quotas = contract.getQuotas()
                .stream()
                .map(this::toRequestQuota)
                .collect(Collectors.toList());
        return new ContractCreationRequest(contract.getNumber(), contract.getDate(), contract.getBankId(), quotas, contract.getCalcType());
    }

    private ContractCreationRequest.QuotaData toRequestQuota(ContractCreation.Quota quota) {
        return new ContractCreationRequest.QuotaData(quota.getNumber(), quota.getAmount(), quota.getDate(), quota.getStatus());
    }
}
