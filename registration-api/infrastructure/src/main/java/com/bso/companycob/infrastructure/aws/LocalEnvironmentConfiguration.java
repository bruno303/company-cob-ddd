package com.bso.companycob.infrastructure.aws;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@EnableAutoConfiguration(exclude = { ContextInstanceDataAutoConfiguration.class })
@Configuration
@Profile(EnvironmentAws.LOCAL)
public class LocalEnvironmentConfiguration {

}
