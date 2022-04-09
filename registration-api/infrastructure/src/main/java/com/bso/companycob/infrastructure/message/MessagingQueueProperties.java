package com.bso.companycob.infrastructure.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "messaging.queues")
@Configuration
public class MessagingQueueProperties {
    private String testeQueue;
}
