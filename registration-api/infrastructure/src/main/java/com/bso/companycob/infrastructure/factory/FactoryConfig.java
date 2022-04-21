package com.bso.companycob.infrastructure.factory;

import com.bso.companycob.application.factory.BankFactory;
import com.bso.companycob.application.factory.BankFactoryImpl;
import com.bso.companycob.application.factory.ContractFactory;
import com.bso.companycob.application.factory.ContractFactoryImpl;
import com.bso.companycob.application.factory.QuotaFactory;
import com.bso.companycob.application.factory.QuotaFactoryImpl;
import com.bso.companycob.domain.repositories.BankRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FactoryConfig {

    @Bean
    public ContractFactory contractFactory(BankRepository bankRepository) {
        return new ContractFactoryImpl(bankRepository);
    }

    @Bean
    public BankFactory bankFactory() {
        return new BankFactoryImpl();
    }

    @Bean
    public QuotaFactory quotaFactory() {
        return new QuotaFactoryImpl();
    }
}
