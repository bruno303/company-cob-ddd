package com.bso.companycob.registration.controller;

import com.bso.companycob.application.core.bus.request.ContractCreationRequest;
import com.bso.companycob.application.core.bus.response.ContractCreationResponse;
import com.bso.companycob.application.model.message.MessageSender;
import com.bso.companycob.registration.config.CompanyCobController;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CompanyCobController
@RequiredArgsConstructor
public class MessageController {

    private final MessageSender messageSender;

    @PostMapping("v1/message")
    public ResponseEntity<ContractCreationResponse> sendMessage(@RequestBody MessageRequest request) {
        messageSender.send(request.getBody(), request.getQueue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequiredArgsConstructor
    @Getter
    public static class MessageRequest {
        private final String body;
        private final String queue;
    }
}
