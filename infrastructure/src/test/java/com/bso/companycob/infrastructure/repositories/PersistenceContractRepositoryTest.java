package com.bso.companycob.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.tests.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceContractRepositoryTest extends AbstractIntegrationTest {
    
    @Autowired
    private PersistenceContractRepository contractRepository;

    @Autowired
    private PersistenceBankRepository bankRepository;

    @Test
    public void testSaveContract() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        Contract contractSaved = contractRepository.save(contract);
        assertThat(contractSaved.getId()).isEqualTo(contract.getId());
        assertThat(contractSaved.getNumber()).isEqualTo(contract.getNumber());
        assertThat(contractSaved.getDate()).isEqualTo(contract.getDate());
        assertThat(contractSaved.getCalcType()).isEqualTo(contract.getCalcType());

        Bank bank = contractSaved.getBank();
        assertThat(bank.getId()).isEqualTo(contract.getBank().getId());
        assertThat(bank.getName()).isEqualTo(contract.getBank().getName());
        assertThat(bank.getInterestRate()).isEqualByComparingTo(contract.getBank().getInterestRate());
    }

    @Test
    public void testFindContractById() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        contractRepository.save(contract);

        Optional<Contract> bankFoundOpt = contractRepository.findById(contract.getId());
        assertThat(bankFoundOpt).isPresent();
    }

    @Test
    public void testFindAllContracts() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        contractRepository.save(contract);

        List<Contract> allContracts = contractRepository.findAll();
        assertThat(allContracts.size()).isEqualTo(1);
    }

    @Test
    public void testDeleteContract() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        contractRepository.save(contract);

        List<Contract> allContracts = contractRepository.findAll();
        assertThat(allContracts.size()).isEqualTo(1);

        contractRepository.delete(contract);

        allContracts = contractRepository.findAll();
        assertThat(allContracts).isEmpty();
    }

    private Contract createContract(UUID id, String number) {
        var contract = new Contract();
        contract.setId(id);
        contract.setNumber(number);
        contract.setDate(LocalDate.now());
        contract.setCalcType(CalcType.DEFAULT.getValue());
        contract.setBank(createBank());

        return contract;
    }

    private Bank createBank() {
        var bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setName("Bank");
        bank.setInterestRate(BigDecimal.valueOf(0.2));

        return bankRepository.save(bank);
    }

}
