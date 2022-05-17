package com.bso.companycob.registration.controller;

import com.bso.companycob.application.bus.request.ContractGetAllRequest;
import com.bso.companycob.application.bus.request.ContractGetRequest;
import com.bso.companycob.application.bus.response.ContractGetAllResponse;
import com.bso.companycob.application.bus.response.ContractGetResponse;
import com.bso.companycob.registration.config.CompanyCobController;
import com.bso.dracko.mediator.contract.Mediator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@CompanyCobController
@RequiredArgsConstructor
public class ContractReadController {

    private final Mediator mediator;

    @GetMapping("v1/contract")
    public ResponseEntity<List<ContractGetAllResponse>> getAllContracts() {
        List<ContractGetAllResponse> contracts = mediator.dispatch(new ContractGetAllRequest());
        return new ResponseEntity<>(contracts, HttpStatus.OK);
    }

    @GetMapping("v1/contract/{id}")
    public ResponseEntity<ContractGetResponse> getContractById(@PathVariable("id") UUID id) {
        var contractOpt = mediator.dispatch(new ContractGetRequest(id));
        if (contractOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(contractOpt.get(), HttpStatus.OK);
    }
}
