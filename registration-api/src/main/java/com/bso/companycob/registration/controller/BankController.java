package com.bso.companycob.registration.controller;

import com.bso.companycob.application.dto.bank.BankCreationDTO;
import com.bso.companycob.application.service.BankCrudService;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.registration.dto.BankResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bank")
@RequiredArgsConstructor
public class BankController {

    private final BankCrudService bankCrudService;

    @GetMapping
    public ResponseEntity<List<BankResponse>> getAllBanks() {
        List<Bank> all = bankCrudService.findAll();
        return new ResponseEntity<>(toResponse(all), HttpStatus.OK);
    }

    @PostMapping
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
