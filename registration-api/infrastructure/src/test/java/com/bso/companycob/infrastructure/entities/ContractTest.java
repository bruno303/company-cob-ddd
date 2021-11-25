package com.bso.companycob.infrastructure.entities;

import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ContractTest {

    private Contract contract;

    @BeforeEach
    public void setup() {
        contract = createContract();
    }

    @Test
    public void toDomainContractMustConvertContractCorrectly() {
        var domainContract = this.contract.toDomainContract();
        assertThat(domainContract.getId()).isEqualByComparingTo(contract.getId());
        assertThat(domainContract.getDate()).isEqualTo(contract.getDate());
        assertThat(domainContract.getCalcType()).isEqualByComparingTo(CalcType.fromValue(contract.getCalcType()));
        assertThat(domainContract.getNumber()).isEqualTo(contract.getNumber());
    }

    @Test
    public void toDomainContractMustConvertBankCorrectly() {
        var domainContract = this.contract.toDomainContract();
        var domainBank = domainContract.getBank();
        var bank = contract.getBank();

        assertThat(domainBank.getId()).isEqualByComparingTo(bank.getId());
        assertThat(domainBank.getInterestRate()).isEqualByComparingTo(bank.getInterestRate());
        assertThat(domainBank.getName()).isEqualTo(bank.getName());
    }

    @Test
    public void toDomainContractMustConvertQuotasCorrectly() {
        var domainContract = this.contract.toDomainContract();
        var domainQuotas = domainContract.getQuotas();
        var quotas = contract.getQuotas();

        assertThat(domainQuotas.size()).isEqualTo(quotas.size());
        assertThat(quotas).hasSize(1);

        var domainQuota = domainQuotas.getQuotas().get(0);
        var quota = quotas.get(0);

        assertThat(domainQuota.getId()).isEqualByComparingTo(quota.getId());
        assertThat(domainQuota.getAmount()).isEqualByComparingTo(quota.getAmount());
        assertThat(domainQuota.getUpdatedAmount()).isEqualByComparingTo(quota.getUpdatedAmount());
        assertThat(domainQuota.getDate()).isEqualTo(quota.getDate());
        assertThat(domainQuota.getDateUpdated()).isEqualTo(quota.getDateUpdated());
        assertThat(domainQuota.getNumber()).isEqualTo(quota.getNumber());
    }

    @Test
    public void fromDomainContractMustConvertContractCorrectly() {
        var domainContract = this.contract.toDomainContract();
        Contract persistenceContract = Contract.fromDomainContract(domainContract);

        assertThat(persistenceContract.getId()).isEqualByComparingTo(contract.getId());
        assertThat(persistenceContract.getDate()).isEqualTo(contract.getDate());
        assertThat(persistenceContract.getCalcType()).isEqualByComparingTo(contract.getCalcType());
        assertThat(persistenceContract.getNumber()).isEqualTo(contract.getNumber());
    }

    @Test
    public void fromDomainContractMustConvertBankCorrectly() {
        var domainContract = this.contract.toDomainContract();
        Contract persistenceContract = Contract.fromDomainContract(domainContract);
        Bank persistenceBank = persistenceContract.getBank();
        var bank = contract.getBank();

        assertThat(persistenceBank.getId()).isEqualByComparingTo(bank.getId());
        assertThat(persistenceBank.getInterestRate()).isEqualByComparingTo(bank.getInterestRate());
        assertThat(persistenceBank.getName()).isEqualTo(bank.getName());
    }

    @Test
    public void fromDomainContractMustConvertQuotasCorrectly() {
        var domainContract = this.contract.toDomainContract();
        Contract persistenceContract = Contract.fromDomainContract(domainContract);

        var persistenceQuotas = persistenceContract.getQuotas();
        var quotas = contract.getQuotas();

        assertThat(persistenceQuotas.size()).isEqualTo(quotas.size());
        assertThat(quotas).hasSize(1);

        var persistenceQuota = persistenceQuotas.get(0);
        var quota = quotas.get(0);

        assertThat(persistenceQuota.getId()).isEqualByComparingTo(quota.getId());
        assertThat(persistenceQuota.getAmount()).isEqualByComparingTo(quota.getAmount());
        assertThat(persistenceQuota.getUpdatedAmount()).isEqualByComparingTo(quota.getUpdatedAmount());
        assertThat(persistenceQuota.getDate()).isEqualTo(quota.getDate());
        assertThat(persistenceQuota.getDateUpdated()).isEqualTo(quota.getDateUpdated());
        assertThat(persistenceQuota.getNumber()).isEqualTo(quota.getNumber());
    }

    private Contract createContract() {
        var contract = new Contract();
        contract.setId(UUID.randomUUID());
        contract.setNumber("XPTO");
        contract.setDate(LocalDate.now());
        contract.setCalcType(CalcType.DEFAULT.getValue());
        contract.setBank(createBank(contract));
        contract.setQuotas(List.of(createQuota(contract)));

        return contract;
    }

    private Quota createQuota(Contract contract) {
        var quota = new Quota();
        quota.setId(UUID.randomUUID());
        quota.setDate(LocalDate.now());
        quota.setNumber(1);
        quota.setAmount(BigDecimal.TEN);
        quota.setUpdatedAmount(BigDecimal.valueOf(25));
        quota.setStatus(QuotaStatus.OPEN.getValue());
        quota.setDateUpdated(LocalDate.now());
        quota.setContract(contract);

        return quota;
    }

    private Bank createBank(Contract contract) {
        var bank = new Bank();
        bank.setId(UUID.randomUUID());
        bank.setInterestRate(BigDecimal.valueOf(0.1));
        bank.setName("Bank 1");
        bank.setContracts(List.of(contract));
        return bank;
    }
}