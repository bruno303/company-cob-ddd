package com.bso.companycob.infrastructure.message;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

@Component
@RequiredArgsConstructor
@Profile("!tests")
public class SqsMessageDeleter {

    private final SqsAsyncClient sqsAsyncClient;

    public void delete(String queue, String receiptHandle) {
        sqsAsyncClient.deleteMessage(DeleteMessageRequest
                .builder()
                .queueUrl(queue)
                .receiptHandle(receiptHandle)
                .build());
    }

}
