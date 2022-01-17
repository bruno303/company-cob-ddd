package com.bso.companycob.registration.controller;

import com.bso.companycob.application.model.bus.Mediator;
import com.bso.companycob.application.core.bus.request.ContractCreationRequest;
import com.bso.companycob.application.core.bus.response.ContractCreationResponse;
import com.bso.companycob.registration.config.CompanyCobController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CompanyCobController
@RequiredArgsConstructor
public class ContractWriteController {

    private final Mediator mediator;

    @PostMapping("v1/contract")
    public ResponseEntity<ContractCreationResponse> createContract(@RequestBody ContractCreationRequest cmd) {
        var response = mediator.dispatch(cmd);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
