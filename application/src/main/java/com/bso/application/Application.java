package com.bso.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.domain.entity.Bank;
import com.bso.companycob.domain.entity.Contract;
import com.bso.companycob.domain.entity.Quota;
import com.bso.companycob.domain.entity.QuotaCollection;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.events.EventRaiser;
import com.bso.companycob.domain.repositories.ContractRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ContractRepository contractRepository() {
        return new DummyContractRepository();
    }

    @Bean
    public CommandLineRunner test(EventRaiser eventRaiser) {
        return args -> {
            Bank bank = new Bank(UUID.randomUUID(), "Bank", BigDecimal.valueOf(0.1));
            Quota quota = new Quota(UUID.randomUUID(), 1, BigDecimal.TEN, BigDecimal.TEN, LocalDate.now(), QuotaStatus.OPEN, LocalDate.now());
            QuotaCollection quotas = new QuotaCollection(List.of(quota));
            Contract contract = new Contract(UUID.randomUUID(), "1", LocalDate.now(), bank, quotas, CalcType.DEFAULT, eventRaiser);
            contract.receivePayment(BigDecimal.TEN);
        };
    }

    public static class DummyContractRepository implements ContractRepository {

        @Override
        public Optional<Contract> findById(UUID id) {
            return null;
        }

        @Override
        public List<Contract> findAll() {
            return null;
        }

        @Override
        public Contract save(Contract entity) {
            return null;
        }

        @Override
        public void delete(Contract entity) {
                        
        }

    }
}
