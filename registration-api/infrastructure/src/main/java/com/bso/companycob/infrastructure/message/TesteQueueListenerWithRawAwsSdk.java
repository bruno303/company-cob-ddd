package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.async.AsyncRunner;
import com.bso.companycob.application.json.JsonUtil;
import com.bso.companycob.application.message.MessageListener;
import com.bso.companycob.application.messaging.MessageProcessor;
import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@OnUseRawAwsSdkEnabled
@Profile("!tests")
public class TesteQueueListenerWithRawAwsSdk extends SqsMessageReaderAdapter implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesteQueueListenerWithRawAwsSdk.class);

    private final MessagingQueueProperties messagingQueueProperties;
    private final SqsMessageDeleter sqsMessageDeleter;
    private final AsyncRunner asyncRunner;
    private final ExecutorService listenerExecutor = Executors.newFixedThreadPool(4);
    private final ExecutorService processorExecutor = Executors.newFixedThreadPool(4);
    private final MessageProcessor messageProcessor;

    public TesteQueueListenerWithRawAwsSdk(SqsClient sqsClient, JsonUtil jsonUtil, MessagingQueueProperties messagingQueueProperties,
                                           SqsMessageDeleter sqsMessageDeleter, AsyncRunner asyncRunner,
                                           @Qualifier(MessageProcessor.TESTE_MESSAGE_PROCESSOR) MessageProcessor messageProcessor) {
        super(sqsClient, jsonUtil);
        this.messagingQueueProperties = messagingQueueProperties;
        this.sqsMessageDeleter = sqsMessageDeleter;
        this.asyncRunner = asyncRunner;
        this.messageProcessor = messageProcessor;
    }

    @PostConstruct
    public void start() {
        LOGGER.info("Using raw sdk listener for queue {}", messagingQueueProperties.getTesteQueue());
        read();
    }

    @Override
    public void read() {
        asyncRunner.run(this::doRead, Executors.newSingleThreadExecutor());
    }

    private void doRead() {
        while (true) {
            asyncRunner.run(() -> super.read(messagingQueueProperties.getTesteQueue()), listenerExecutor)
                    .thenAcceptAsync(messages -> messages.forEach(this::handle), processorExecutor);
            sleep();
        }
    }

    private void handle(Message message) {
        var body = message.body();
        messageProcessor.process(body);
        sqsMessageDeleter.delete(messagingQueueProperties.getTesteQueue(), message.receiptHandle());
    }

    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
