package com.bso.companycob.registration.controller;

import com.bso.companycob.application.core.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.core.bus.response.ContractGetAllResponse;
import com.bso.companycob.registration.config.CompanyCobController;
import com.bso.dracko.mediator.contract.Mediator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

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
