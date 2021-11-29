package com.bso.companycob.application.service;

import com.bso.companycob.application.dto.bank.BankCreationDTO;
import com.bso.companycob.application.factory.bank.BankFactory;
import com.bso.companycob.domain.entity.bank.Bank;
import com.bso.companycob.domain.repositories.BankRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        when(bankFactoryMock.create(eq(bankName), any(BigDecimal.class))).thenReturn(bank);
        when(bankRepositoryMock.saveAndFlush(any(Bank.class))).thenReturn(bank);

        var dto = new BankCreationDTO(bankName, interestRate);
        Bank bankCreated = bankCrudService.create(dto);
        assertThat(bankCreated.getId()).isEqualByComparingTo(bank.getId());
        assertThat(bankCreated.getName()).isEqualTo(bankName);
        assertThat(bankCreated.getInterestRate()).isEqualByComparingTo(interestRate);

        verify(bankFactoryMock, times(1)).create(eq(bankName), any(BigDecimal.class));
        verify(bankRepositoryMock, times(1)).saveAndFlush(any(Bank.class));
    }

    @Test
    public void testFindAllCallFindAllMethodFromRepository() {
        when(bankRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        List<Bank> all = bankCrudService.findAll();
        assertThat(all).hasSize(0);

        verify(bankRepositoryMock, times(1)).findAll();
    }
}