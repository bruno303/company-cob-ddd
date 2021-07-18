package com.bso.companycob.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.tests.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceBankRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private PersistenceBankRepository bankRepository;
    
    @Test
    public void testSaveBank() {
        Bank bank = createBank(UUID.randomUUID(), "Bank", BigDecimal.valueOf(0.1));
        Bank bankSaved = bankRepository.save(bank);
        assertThat(bankSaved.getId()).isEqualTo(bank.getId());
        assertThat(bankSaved.getName()).isEqualTo(bank.getName());
        assertThat(bankSaved.getInterestRate()).isEqualByComparingTo(bank.getInterestRate());
    }

    @Test
    public void testFindBankById() {
        Bank bank = createBank(UUID.randomUUID(), "Bank", BigDecimal.valueOf(0.1));
        bankRepository.save(bank);

        Optional<Bank> bankFoundOpt = bankRepository.findById(bank.getId());
        assertThat(bankFoundOpt).isPresent();
    }

    @Test
    public void testFindAllBanks() {
        Bank bank = createBank(UUID.randomUUID(), "Bank", BigDecimal.valueOf(0.1));
        bankRepository.save(bank);

        List<Bank> allBanks = bankRepository.findAll();
        assertThat(allBanks.size()).isEqualTo(1);
    }

    @Test
    public void testDeleteBank() {
        Bank bank = createBank(UUID.randomUUID(), "Bank", BigDecimal.valueOf(0.1));
        bankRepository.save(bank);

        List<Bank> allBanks = bankRepository.findAll();
        assertThat(allBanks.size()).isEqualTo(1);

        bankRepository.delete(bank);

        allBanks = bankRepository.findAll();
        assertThat(allBanks).isEmpty();
    }

    private Bank createBank(UUID id, String name, BigDecimal interestRate) {
        var bank = new Bank();
        bank.setId(id);
        bank.setName(name);
        bank.setInterestRate(interestRate);

        return bank;
    }
}
