package com.bso.companycob.infrastructure.message;

import com.bso.companycob.application.messaging.MessageProcessor;
import com.bso.companycob.application.testemessage.TestMessageProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProcessorConfig {

    @Bean(name = MessageProcessor.TESTE_MESSAGE_PROCESSOR)
    public MessageProcessor testeMessageProcessor() {
        return new TestMessageProcessor();
    }

}
