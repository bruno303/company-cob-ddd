package com.bso.companycob.application.events.paymentreceived;

import com.bso.companycob.domain.events.EventHandler;
import com.bso.companycob.domain.events.impl.paymentreceived.PaymentReceivedEvent;
import com.bso.companycob.domain.events.impl.paymentreceived.PrintMessagePaymentReceivedEventHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentReceivedEventConfiguration {
    
    @Bean
    public EventHandler<PaymentReceivedEvent> PrintMessagePaymentReceivedEventHandler() {
        return new PrintMessagePaymentReceivedEventHandler();
    }

}
