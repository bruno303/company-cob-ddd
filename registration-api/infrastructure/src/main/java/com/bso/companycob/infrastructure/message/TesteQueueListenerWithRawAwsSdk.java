package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.model.json.JsonUtil;
import com.bso.companycob.application.model.message.MessageListener;
import com.bso.companycob.infrastructure.aws.OnUseRawAwsSdkEnabled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@OnUseRawAwsSdkEnabled
public class TesteQueueListenerWithRawAwsSdk extends SqsMessageReaderAdapter implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(TesteQueueListenerWithRawAwsSdk.class);

    private final MessagingQueueProperties messagingQueueProperties;
    private final SqsMessageDeleter sqsMessageDeleter;
    private final ExecutorService executor = Executors.newFixedThreadPool(4);

    public TesteQueueListenerWithRawAwsSdk(SqsClient sqsClient, JsonUtil jsonUtil, MessagingQueueProperties messagingQueueProperties,
                                           SqsMessageDeleter sqsMessageDeleter) {
        super(sqsClient, jsonUtil);
        this.messagingQueueProperties = messagingQueueProperties;
        this.sqsMessageDeleter = sqsMessageDeleter;
    }

    @PostConstruct
    public void start() {
        LOGGER.info("Using raw sdk listener for queue {}", messagingQueueProperties.getTesteQueue());
        read();
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void read() {
        CompletableFuture.runAsync(() -> {
            while (true) {
                var messages = super.read(messagingQueueProperties.getTesteQueue());
                messages.forEach(this::handle);
                sleep();
            }
        }, executor);
    }

    private void handle(Message message) {
        var body = message.body();
        LOGGER.info("Message received: {}", body);
        sqsMessageDeleter.delete(messagingQueueProperties.getTesteQueue(), message.receiptHandle());
    }

    private void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
