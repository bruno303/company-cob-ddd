package com.bso.companycob.infrastructure.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.infrastructure.entities.Quota;
import com.bso.companycob.infrastructure.utils.Fixture;
import com.bso.companycob.tests.AbstractIntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class PersistenceContractRepositoryTest extends AbstractIntegrationTest {
    
    @Autowired
    private PersistenceContractRepository contractRepository;

    @Autowired
    private Fixture fixture;

    @Test
    public void testSaveContract() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        Contract contractSaved = contractRepository.save(contract);
        assertThat(contractSaved.getId()).isEqualTo(contract.getId());
        assertThat(contractSaved.getNumber()).isEqualTo(contract.getNumber());
        assertThat(contractSaved.getDate()).isEqualTo(contract.getDate());
        assertThat(contractSaved.getCalcType()).isEqualTo(contract.getCalcType());
        assertThat(contractSaved.getBankId()).isEqualTo(contract.getBankId());
    }

    @Test
    public void testFindContractById() {
        Contract contract = createContract(UUID.randomUUID(), "123456");
        contractRepository.save(contract);

        Optional<Contract> contractFoundOpt = contractRepository.findById(contract.getId());
        assertThat(contractFoundOpt).isPresent();
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
        Bank bank = createBank();

        var contract = new Contract();
        contract.setId(id);
        contract.setNumber(number);
        contract.setDate(LocalDate.now());
        contract.setCalcType(CalcType.DEFAULT.getValue());
        contract.setBank(bank);
        contract.setBankId(bank.getId());
        contract.setQuotas(createQuotas(contract, 2));

        return contractRepository.save(contract);
    }

    private List<Quota> createQuotas(Contract contract, int amount) {
        List<Quota> quotas = new ArrayList<>(amount);

        for (int i = 0; i < amount; i++) {
            Quota quota = new Quota();
            quota.setId(UUID.randomUUID());
            quota.setAmount(BigDecimal.TEN);
            quota.setDate(LocalDate.now());
            quota.setDateUpdated(LocalDate.now());
            quota.setNumber(1);
            quota.setStatus(1);
            quota.setUpdatedAmount(BigDecimal.TEN);
            quota.setContractId(contract.getId());

            quotas.add(quota);
        }

        return quotas;
    }

    private Bank createBank() {
        var bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setName("Bank");
        bank.setInterestRate(BigDecimal.valueOf(0.2));

        return fixture.save(bank);
    }

}
