package com.bso.companycob.application.core.service;

import com.bso.companycob.application.core.service.BankCrudService;
import com.bso.companycob.application.model.dto.bank.BankCreationDTO;
import com.bso.companycob.application.model.factory.BankFactory;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class BankCrudServiceTest {

    private BankCrudService bankCrudService;
    private final BankFactory bankFactoryMock = Mockito.mock(BankFactory.class);
    private final BankRepository bankRepositoryMock = Mockito.mock(BankRepository.class);

    @BeforeEach
    public void setup() {
        bankCrudService = new BankCrudService(bankFactoryMock, bankRepositoryMock);
    }

    @Test
    public void createShouldCallFactoryAndRepository() {
        final String bankName = "Bank 1";
        final BigDecimal interestRate = BigDecimal.valueOf(0.1);
        final Bank bank = new Bank(UUID.randomUUID(), bankName, interestRate);
        Mockito.when(bankFactoryMock.create(ArgumentMatchers.eq(bankName), ArgumentMatchers.any(BigDecimal.class))).thenReturn(bank);
        Mockito.when(bankRepositoryMock.saveAndFlush(ArgumentMatchers.any(Bank.class))).thenReturn(bank);

        var dto = new BankCreationDTO(bankName, interestRate);
        Bank bankCreated = bankCrudService.create(dto);
        Assertions.assertThat(bankCreated.getId()).isEqualByComparingTo(bank.getId());
        Assertions.assertThat(bankCreated.getName()).isEqualTo(bankName);
        Assertions.assertThat(bankCreated.getInterestRate()).isEqualByComparingTo(interestRate);

        Mockito.verify(bankFactoryMock, Mockito.times(1)).create(ArgumentMatchers.eq(bankName), ArgumentMatchers.any(BigDecimal.class));
        Mockito.verify(bankRepositoryMock, Mockito.times(1)).saveAndFlush(ArgumentMatchers.any(Bank.class));
    }

    @Test
    public void testFindAllCallFindAllMethodFromRepository() {
        Mockito.when(bankRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<Bank> all = bankCrudService.findAll();
        Assertions.assertThat(all).hasSize(0);

        Mockito.verify(bankRepositoryMock, Mockito.times(1)).findAll();
    }
}
