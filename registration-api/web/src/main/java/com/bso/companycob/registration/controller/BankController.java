package com.bso.companycob.registration.controller;

import com.bso.companycob.application.core.dto.bank.BankCreationDTO;
import com.bso.companycob.application.core.service.BankCrudService;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.registration.config.CompanyCobController;
import com.bso.companycob.registration.dto.BankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@CompanyCobController
@RequiredArgsConstructor
public class BankController {

    private final BankCrudService bankCrudService;

    @GetMapping("v1/bank")
    public ResponseEntity<List<BankResponse>> getAllBanks() {
        List<Bank> all = bankCrudService.findAll();
        return new ResponseEntity<>(toResponse(all), HttpStatus.OK);
    }

    @PostMapping("v1/bank")
    public ResponseEntity<BankResponse> createBank(@RequestBody BankCreationDTO dto) {
        Bank bank = bankCrudService.create(dto);
        return new ResponseEntity<>(toResponse(bank), HttpStatus.CREATED);
    }

    private List<BankResponse> toResponse(List<Bank> bank) {
        var response = new ArrayList<BankResponse>(bank.size());
        bank.forEach(b -> response.add(toResponse(b)));
        return response;
    }

    private BankResponse toResponse(Bank bank) {
        return new BankResponse(bank.getId(), bank.getName(), bank.getInterestRate());
    }
}
