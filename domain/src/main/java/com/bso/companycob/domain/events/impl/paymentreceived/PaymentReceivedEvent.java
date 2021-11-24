package com.bso.companycob.domain.events.impl.paymentreceived;

import com.bso.companycob.domain.events.Event;

public class PaymentReceivedEvent implements Event {
    private final String message;

    public PaymentReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
