package com.bso.companycob.tests;

import com.bso.companycob.tests.configuration.TestContextConfiguration;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { TestContextConfiguration.class })
@ActiveProfiles("tests")
public abstract class AbstractIntegrationTest {
    
}
