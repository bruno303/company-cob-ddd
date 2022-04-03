package com.bso.companycob.application.model.message;

public interface MessageSender {
    <T> void send(T object, String queue);
}
