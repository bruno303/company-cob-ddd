package com.bso.companycob.registration.controller;

import com.bso.companycob.application.dto.contract.ContractCreationDTO;
import com.bso.companycob.application.service.ContractCrudService;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.entity.contract.Contract;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.registration.config.CompanyCobController;
import com.bso.companycob.registration.dto.BankResponse;
import com.bso.companycob.registration.dto.ContractResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CompanyCobController
@RequiredArgsConstructor
public class ContractController {

    private final ContractCrudService contractCrudService;

    @GetMapping("v1/contract")
    public ResponseEntity<List<ContractResponse>> getAllContracts() {
        List<Contract> contracts = contractCrudService.findAll();
        return new ResponseEntity<>(toResponse(contracts), HttpStatus.OK);
    }

    @PostMapping("v1/contract")
    public ResponseEntity<ContractResponse> createContract(@RequestBody ContractCreationDTO dto) {
        Contract contract = contractCrudService.createContract(dto);
        return new ResponseEntity<>(toResponse(contract), HttpStatus.CREATED);
    }

    private List<ContractResponse> toResponse(List<Contract> contracts) {
        return contracts.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private ContractResponse toResponse(Contract contract) {
        var bankResponse = toBankResponse(contract);
        var quotaResponse = toQuotaResponse(contract);

        return ContractResponse.builder()
                .id(contract.getId())
                .calcType(contract.getCalcType())
                .date(contract.getDate())
                .number(contract.getNumber())
                .bank(bankResponse)
                .quotas(quotaResponse)
                .build();
    }

    private List<ContractResponse.QuotasResponse> toQuotaResponse(Contract contract) {
        List<Quota> quotas = contract.getQuotas().getQuotas();
        List<ContractResponse.QuotasResponse> quotasResponse = new ArrayList<>(quotas.size());
        quotas.forEach(q -> {
            var quotaResponse = ContractResponse.QuotasResponse.builder()
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

    private BankResponse toBankResponse(Contract contract) {
        Bank bank = contract.getBank();
        return new BankResponse(bank.getId(), bank.getName(), bank.getInterestRate());
    }
}
