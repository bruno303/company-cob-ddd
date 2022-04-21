package com.bso.companycob.application.message;

public interface MessageSender {
    <T> void send(T object, String queue);
    <T> void sendAsync(T object, String queue);
}
