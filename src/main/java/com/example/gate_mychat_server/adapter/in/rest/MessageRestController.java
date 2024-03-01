package com.example.gate_mychat_server.adapter.in.rest;

import com.example.gate_mychat_server.adapter.in.rest.util.ConvertToJSON;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.port.out.MessageHistoryPort;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/api/v1/message")
@RestController
public class MessageRestController {
    private final MessageHistoryPort messageHistoryPort;

    public MessageRestController(MessageHistoryPort messageHistoryPort) {
        this.messageHistoryPort = messageHistoryPort;
    }

    @GetMapping(value ="/getLastMessageWithAllFriendsUser", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getLastMessagesWithAllFriends(@RequestParam @Valid Long idUser) {
        return ConvertToJSON.convert(messageHistoryPort.getLastMessagesWithFriends(Mono.just(new IdUserData(idUser))));
    }


}
