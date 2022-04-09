package com.bso.companycob.infrastructure.message;

import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkDisabled;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@OnUseRawAwsSdkDisabled
public class TesteQueueListenerWithSpringCloud {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesteQueueListenerWithSpringCloud.class);

    @Value("${messaging.queues.teste-queue}")
    private String queueName;

    @PostConstruct
    public void init() {
        LOGGER.info("Using spring cloud aws listener for queue {}", queueName);
    }

    @SqsListener("${messaging.queues.teste-queue}")
    public void listen(String messageBody) {
        LOGGER.info("Message received: {}", messageBody);
    }

}
