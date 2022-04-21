package com.bso.companycob.application.testemessage;

import com.bso.companycob.application.messaging.MessageProcessor;

public class TestMessageProcessor implements MessageProcessor {

    public void process(String messageBody) {
        System.out.printf("Message received: %s%n", messageBody);
    }
}
