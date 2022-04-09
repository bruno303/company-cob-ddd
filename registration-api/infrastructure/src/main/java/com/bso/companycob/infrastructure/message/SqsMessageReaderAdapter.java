package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.model.json.JsonUtil;
import com.bso.companycob.application.model.message.MessageReader;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@RequiredArgsConstructor
public abstract class SqsMessageReaderAdapter implements MessageReader<Message> {

    protected final SqsClient sqsClient;
    protected final JsonUtil jsonUtil;

    @Override
    public List<Message> read(String queue) {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(queue)
                .waitTimeSeconds(10)
                .maxNumberOfMessages(10)
                .build();

        return sqsClient.receiveMessage(receiveMessageRequest)
                .messages();
    }

}
