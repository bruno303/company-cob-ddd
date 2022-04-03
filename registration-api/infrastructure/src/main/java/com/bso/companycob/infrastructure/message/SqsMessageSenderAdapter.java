package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.model.message.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
@RequiredArgsConstructor
public class SqsMessageSenderAdapter implements MessageSender {

    private final SqsClient sqsClient;

    @Override
    public <T> void send(T object, String queue) {
        var request = SendMessageRequest.builder()
                .messageBody(object.toString())
                .queueUrl(queue)
                .delaySeconds(1)
                .build();
        sqsClient.sendMessage(request);
    }
}
