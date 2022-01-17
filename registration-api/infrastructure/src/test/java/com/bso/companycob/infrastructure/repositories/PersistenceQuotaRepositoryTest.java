package com.bso.companycob.infrastructure.repositories;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.infrastructure.entities.Bank;
import com.bso.companycob.infrastructure.entities.Contract;
import com.bso.companycob.infrastructure.entities.Quota;
import com.bso.companycob.infrastructure.utils.Fixture;
import com.bso.companycob.tests.AbstractIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PersistenceQuotaRepositoryTest extends AbstractIntegrationTest {
    
    @Autowired
    private PersistenceQuotaRepository quotaRepository;

    @Autowired
    private Fixture fixture;

    private Contract contract;

    @BeforeEach
    public void setup() {
        contract = createContract(UUID.randomUUID(), UUID.randomUUID().toString());
    }

    @Test
    public void testSaveQuota() {
        Quota quota = createQuota();
        Quota quotaSaved = quotaRepository.save(quota);
        assertThat(quotaSaved.getId()).isEqualTo(quota.getId());
        assertThat(quotaSaved.getNumber()).isEqualTo(quota.getNumber());
        assertThat(quotaSaved.getDate()).isEqualTo(quota.getDate());
        assertThat(quotaSaved.getAmount()).isEqualByComparingTo(quota.getAmount());
        assertThat(quotaSaved.getUpdatedAmount()).isEqualByComparingTo(quota.getUpdatedAmount());
        assertThat(quotaSaved.getDateUpdated()).isEqualTo(quota.getDateUpdated());
        assertThat(quotaSaved.getStatus()).isEqualTo(quota.getStatus());
    }

    @Test
    public void testFindQuotaById() {
        Quota quota = createQuota();
        quotaRepository.save(quota);

        Optional<Quota> quotaFoundOpt = quotaRepository.findById(quota.getId());
        assertThat(quotaFoundOpt).isPresent();
    }

    @Test
    public void testFindAllQuotas() {
        Quota quota = createQuota();
        Quota quota2 = createQuota();

        quotaRepository.save(quota);
        quotaRepository.save(quota2);

        List<Quota> allQuotas = quotaRepository.findAll();
        assertThat(allQuotas.size()).isEqualTo(2);
    }

    @Test
    public void testDeleteQuota() {
        Quota quota = createQuota();
        quotaRepository.save(quota);

        List<Quota> allQuotas = quotaRepository.findAll();
        assertThat(allQuotas.size()).isEqualTo(1);

        quotaRepository.delete(quota);

        allQuotas = quotaRepository.findAll();
        assertThat(allQuotas).isEmpty();
    }

    private Quota createQuota() {
        var persistenceQuota = new Quota();
        persistenceQuota.setId(UUID.randomUUID());
        persistenceQuota.setNumber(1);
        persistenceQuota.setAmount(BigDecimal.TEN);
        persistenceQuota.setUpdatedAmount(BigDecimal.valueOf(25));
        persistenceQuota.setDate(LocalDate.now().minusDays(1));
        persistenceQuota.setStatus(QuotaStatus.OPEN.getValue());
        persistenceQuota.setDateUpdated(LocalDate.now());
        persistenceQuota.setContractId(contract.getId());

        return persistenceQuota;
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

        return fixture.save(contract);
    }

    private Bank createBank() {
        var bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setName("Bank");
        bank.setInterestRate(BigDecimal.valueOf(0.2));

        return fixture.save(bank);
    }

}
