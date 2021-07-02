package com.bso.companycob.tests;

import com.bso.companycob.tests.configuration.TestContextConfiguration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestContextConfiguration.class })
public abstract class AbstractIntegrationTest {
    
}
