package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.messaging.MessageProcessor;
import com.bso.companycob.application.testemessage.TestMessageProcessor;
import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkDisabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@OnUseRawAwsSdkDisabled
@Profile("!tests")
public class TesteQueueListenerWithSpringCloud {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesteQueueListenerWithSpringCloud.class);

    private final MessageProcessor messageProcessor;

    @Value("${messaging.queues.teste-queue}")
    private String queueName;

    public TesteQueueListenerWithSpringCloud(@Qualifier(MessageProcessor.TESTE_MESSAGE_PROCESSOR) MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Using spring cloud aws listener for queue {}", queueName);
    }

    @SqsListener("${messaging.queues.teste-queue}")
    public void listen(String messageBody) {
        LOGGER.info("Message received: {}", messageBody);
    }

}
