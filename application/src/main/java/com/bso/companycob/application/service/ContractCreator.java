package com.bso.companycob.application.service;

import java.time.LocalDate;
import java.util.List;

import com.bso.companycob.application.events.contract.creation.ContractCreatedEvent;
import com.bso.companycob.application.factory.contract.ContractFactory;
import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.springframework.stereotype.Component;

@Component
public class ContractCreator {

    private final ContractFactory contractFactory;
    private final ContractRepository contractRepository;
    private final EventRaiser eventRaiser;
    
    public ContractCreator(ContractFactory contractFactory, ContractRepository contractRepository, EventRaiser eventRaiser) {
        this.contractFactory = contractFactory;
        this.contractRepository = contractRepository;
        this.eventRaiser = eventRaiser;
    }

    public Contract createContract(ContractCreationDTO dto) {
        Contract contract = contractFactory.create(dto.getNumber(), dto.getDate(), dto.getBank(), dto.getQuotas(), dto.getCalcType());
        contract = contractRepository.saveAndFlush(contract);
        eventRaiser.raise(new ContractCreatedEvent(contract));
        return contract;
    }

    public static class ContractCreationDTO {
        private final String number;
        private final LocalDate date;
        private final Bank bank;
        private final List<Quota> quotas;
        private final CalcType calcType;

        public ContractCreationDTO(String number, LocalDate date, Bank bank, List<Quota> quotas, CalcType calcType) {
            this.number = number;
            this.date = date;
            this.bank = bank;
            this.quotas = quotas;
            this.calcType = calcType;
        }

        public String getNumber() {
            return number;
        }

        public LocalDate getDate() {
            return date;
        }

        public Bank getBank() {
            return bank;
        }

        public List<Quota> getQuotas() {
            return quotas;
        }

        public CalcType getCalcType() {
            return calcType;
        }
    }
}
