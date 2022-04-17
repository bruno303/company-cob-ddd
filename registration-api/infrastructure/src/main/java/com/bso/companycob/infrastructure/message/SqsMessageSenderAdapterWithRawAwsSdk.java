package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.model.json.JsonUtil;
import com.bso.companycob.application.model.message.MessageSender;
import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkEnabled;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@OnUseRawAwsSdkEnabled
@Slf4j
@Profile("!tests")
public class SqsMessageSenderAdapterWithRawAwsSdk implements MessageSender {

    private final SqsClient sqsClient;
    private final SqsAsyncClient sqsAsyncClient;
    private final JsonUtil jsonUtil;

    @PostConstruct
    public void init() {
        log.info("Using raw aws sdk message sender");
    }

    @Override
    public <T> void send(T object, String queue) {
        sqsClient.sendMessage(buildRequest(object, queue));
    }

    @Override
    public <T> void sendAsync(T object, String queue) {
        sqsAsyncClient.sendMessage(buildRequest(object, queue));
    }

    private <T> SendMessageRequest buildRequest(T object, String queue) {
        return SendMessageRequest.builder()
                .messageBody(jsonUtil.toJson(object))
                .queueUrl(queue)
                .delaySeconds(1)
                .build();
    }
}
