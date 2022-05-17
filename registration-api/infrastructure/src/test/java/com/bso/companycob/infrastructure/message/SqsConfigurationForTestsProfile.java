package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.message.MessageListener;
import com.bso.companycob.application.message.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("tests")
public class SqsConfigurationForTestsProfile {

    @Bean
    public MessageListener messageListener() {
        return () -> {};
    }

    @Bean
    public MessageSender messageSender() {
        return new MessageSender() {
            private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

            @Override
            public <T> void send(T object, String queue) {
                LOGGER.info("[{}] Message sent: {}", queue, object);
            }

            @Override
            public <T> void sendAsync(T object, String queue) {
                LOGGER.info("[{}] Message sent async: {}", queue, object);
            }
        };
    }
}
