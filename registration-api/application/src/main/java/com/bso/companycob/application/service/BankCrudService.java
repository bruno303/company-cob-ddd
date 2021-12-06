package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.bank.BankCreationDTO;
import com.bso.companycob.application.factory.bank.BankFactory;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
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
