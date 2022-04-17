package com.bso.companycob.tests.configuration;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.bso.companycob")
@EntityScan(basePackages = "com.bso.companycob.infrastructure.entities")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.bso.companycob.infrastructure.repositories")
public class TestContextConfiguration {
    
}
