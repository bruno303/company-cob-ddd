package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.bank.BankCreationDTO;
import com.bso.companycob.application.factory.BankFactory;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BankCrudService {

    private final BankFactory bankFactory;
    private final BankRepository bankRepository;

    public Bank create(BankCreationDTO dto) {
        Bank bank = bankFactory.create(dto.getName(), dto.getInterestRate());
        return bankRepository.saveAndFlush(bank);
    }

    public List<Bank> findAll() {
        return bankRepository.findAll();
    }
}
