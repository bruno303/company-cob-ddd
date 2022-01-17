package com.bso.companycob.registration.controller;

import com.bso.companycob.application.core.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.core.bus.response.ContractGetAllResponse;
import com.bso.companycob.application.core.handlers.requests.ContractGetAllRequestHandler;
import com.bso.companycob.application.model.bus.Mediator;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CompanyCobController
@RequiredArgsConstructor
public class ContractReadController {

    private final Mediator mediator;

    @GetMapping("v1/contract")
    public ResponseEntity<List<ContractGetAllResponse>> getAllContracts() {
        List<ContractGetAllResponse> contracts = mediator.dispatch(new ContractGetAllRequest());
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }
}
