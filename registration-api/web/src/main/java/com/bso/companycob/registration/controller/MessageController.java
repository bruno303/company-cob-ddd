package com.bso.companycob.registration.controller;

import com.bso.companycob.application.bus.response.ContractCreationResponse;
import com.bso.companycob.application.message.MessageSender;
import com.bso.companycob.application.testemessage.TesteMessageRequest;
import com.bso.companycob.infrastructure.message.MessagingQueueProperties;
import com.bso.companycob.registration.config.CompanyCobController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CompanyCobController
@RequiredArgsConstructor
public class MessageController {

    private final MessageSender messageSender;
    private final MessagingQueueProperties messagingQueueProperties;

    @PostMapping("v1/message")
    public ResponseEntity<ContractCreationResponse> sendMessage(@RequestBody TesteMessageRequest request) {
        messageSender.sendAsync(request, messagingQueueProperties.getTesteQueue());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
