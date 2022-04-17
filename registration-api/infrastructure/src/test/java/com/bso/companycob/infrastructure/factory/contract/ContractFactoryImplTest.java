package com.bso.companycob.infrastructure.factory.contract;

import com.bso.companycob.infrastructure.factory.contract.ContractFactoryImpl;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.entity.contract.Quota;
import com.bso.companycob.domain.enums.CalcType;
import com.bso.companycob.domain.enums.QuotaStatus;
import com.bso.companycob.domain.exception.BankNotFoundException;
import com.bso.companycob.domain.repositories.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ContractFactoryImplTest {

    private ContractFactoryImpl contractFactory;
    private final BankRepository bankRepositoryMock = Mockito.mock(BankRepository.class);

    @BeforeEach
    public void setup() {
        contractFactory = new ContractFactoryImpl(bankRepositoryMock);
    }

    @Test
    public void createMustCallBankRepositoryAndThrowErrorIfReturnEmpty() {
        final UUID bankId = UUID.randomUUID();
        Mockito.when(bankRepositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.empty());

        Quota quota = createQuota();

        UUID id = UUID.randomUUID();

        var exception = assertThrows(BankNotFoundException.class, () -> contractFactory.create(id, "XPTO",
                LocalDate.now(), bankId, List.of(quota), CalcType.DEFAULT));
        assertThat(exception.getBankId()).isEqualByComparingTo(bankId);

        Mockito.verify(bankRepositoryMock, Mockito.times(1)).findById(Mockito.eq(bankId));
    }

    @Test
    public void createMustCallBankRepositoryAndGetSuccess() {
        final UUID bankId = UUID.randomUUID();
        Mockito.when(bankRepositoryMock.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(createBank(bankId)));

        Quota quota = createQuota();

        var contract = contractFactory.create(UUID.randomUUID(), "XPTO", LocalDate.now(), bankId,
                List.of(quota), CalcType.DEFAULT);

        assertThat(contract).isNotNull();

        Mockito.verify(bankRepositoryMock, Mockito.times(1)).findById(Mockito.eq(bankId));
    }

    private Quota createQuota() {
        LocalDate date = LocalDate.now();
        BigDecimal value = BigDecimal.TEN;
        return new Quota(UUID.randomUUID(), 1, value, value, date, QuotaStatus.OPEN, date);
    }

    private Bank createBank(UUID id) {
        return new Bank(id, "Bank", BigDecimal.valueOf(0.1));
    }
}
