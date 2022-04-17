package com.bso.companycob.infrastructure.aws;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;

@EnableAutoConfiguration(exclude = { ContextInstanceDataAutoConfiguration.class })
@Configuration
@Profile(EnvironmentAws.LOCAL)
@Slf4j
public class LocalEnvironmentConfiguration {

    @PostConstruct
    public void log() {
        log.info("Using profile {}. Applying profile configurations", EnvironmentAws.LOCAL);
    }

}
