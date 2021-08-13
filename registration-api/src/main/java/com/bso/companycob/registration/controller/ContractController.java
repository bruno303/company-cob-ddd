package com.bso.companycob.registration.controller;

import com.bso.companycob.application.dto.contract.ContractCreationDTO;
import com.bso.companycob.application.service.ContractCrudService;
import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.registration.dto.BankResponse;
import com.bso.companycob.registration.dto.ContractResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractCrudService contractCrudService;

    @GetMapping
    public ResponseEntity<List<ContractResponse>> getAllContracts() {
        List<Contract> contracts = contractCrudService.findAll();
        return new ResponseEntity<>(toResponse(contracts), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ContractResponse> createContract(@RequestBody ContractCreationDTO dto) {
        Contract contract = contractCrudService.createContract(dto);
        return new ResponseEntity<>(toResponse(contract), HttpStatus.CREATED);
    }

    private List<ContractResponse> toResponse(List<Contract> contracts) {
        List<ContractResponse> result = new ArrayList<>(contracts.size());
        contracts.forEach(c -> result.add(toResponse(c)));
        return result;
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
