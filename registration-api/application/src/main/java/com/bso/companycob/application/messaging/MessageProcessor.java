package com.bso.companycob.application.messaging;

public interface MessageProcessor {

    public static final String TESTE_MESSAGE_PROCESSOR = "TesteMessageProcessor";

    void process(String message);
}
