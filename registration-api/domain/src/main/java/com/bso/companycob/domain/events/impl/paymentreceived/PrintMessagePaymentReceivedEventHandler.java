package com.bso.companycob.domain.events.impl.paymentreceived;

import java.util.concurrent.CompletableFuture;

import com.bso.companycob.domain.events.EventHandler;

public class PrintMessagePaymentReceivedEventHandler implements EventHandler<PaymentReceivedEvent> {

    @Override
    public CompletableFuture<Void> handle(PaymentReceivedEvent eventData) {
        System.out.println("Payment received: " + eventData.getMessage());
        return CompletableFuture.completedFuture(null);
    }
    
}
