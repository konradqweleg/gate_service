package com.example.gate_mychat_server.adapter.in.rest;

import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.IdUserData;

import com.example.gate_mychat_server.model.request.MessageData;
import com.example.gate_mychat_server.port.in.MessageHistoryUseCase;
import com.example.gate_mychat_server.port.in.MessageUseCase;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/api/v1/message")
@RestController
public class MessageRestController {
    private final MessageHistoryUseCase messageHistoryPort;

    private final MessageUseCase messageUseCase;

    public MessageRestController(MessageHistoryUseCase messageHistoryPort, MessageUseCase messageUseCase) {
        this.messageHistoryPort = messageHistoryPort;
        this.messageUseCase = messageUseCase;
    }

    @GetMapping(value ="/getLastMessageWithAllFriendsUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getLastMessagesWithAllFriends(@RequestParam @Valid Long idUser) {
        return ConvertToJSON.convert(messageHistoryPort.getLastMessagesWithFriends(Mono.just(new IdUserData(idUser))));
    }

    @PostMapping(value = "/insertMessage", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> insertMessage(@RequestBody @Valid Mono<MessageData> message) {
        return messageUseCase.insertMessage(message).flatMap(ConvertToJSON::convert);
    }

    @GetMapping(value = "/getMessageBetweenUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getMessageBetweenUsers(@RequestParam @Valid Long idFirstUser, @RequestParam @Valid Long idFriend) {
        return ConvertToJSON.convert(messageUseCase.getMessageBetweenUsers(Mono.just(new IdUserData(idFirstUser)), Mono.just(new IdUserData(idFriend))));
    }

}
