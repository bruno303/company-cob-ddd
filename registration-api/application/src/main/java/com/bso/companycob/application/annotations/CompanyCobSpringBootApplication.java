package com.bso.companycob.application.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.bso.companycob")
@EntityScan(basePackages = "com.bso.companycob.infrastructure.entities")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackages = "com.bso.companycob.infrastructure.repositories")
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CompanyCobSpringBootApplication {
    
}
