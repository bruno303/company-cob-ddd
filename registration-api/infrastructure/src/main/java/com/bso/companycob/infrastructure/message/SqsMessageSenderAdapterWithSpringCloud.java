package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.model.message.MessageSender;
import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkDisabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@OnUseRawAwsSdkDisabled
@Slf4j
@Profile("!tests")
public class SqsMessageSenderAdapterWithSpringCloud implements MessageSender {

    private final QueueMessagingTemplate sqsMessagingTemplate;

    @PostConstruct
    public void init() {
        log.info("Using spring cloud aws message sender");
    }

    @Override
    public <T> void send(T object, String queue) {
        sqsMessagingTemplate.convertAndSend(queue, object);
    }

    @Override
    public <T> void sendAsync(T object, String queue) {
        CompletableFuture.runAsync(() -> send(object, queue));
    }
}
