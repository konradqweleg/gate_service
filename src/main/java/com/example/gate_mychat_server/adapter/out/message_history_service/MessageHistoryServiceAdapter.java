package com.example.gate_mychat_server.adapter.out.message_history_service;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.LastMessageWithUser;
import com.example.gate_mychat_server.port.out.MessageHistoryPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Service
public class MessageHistoryServiceAdapter implements MessageHistoryPort {
    private final String uriMessageHistoryServiceGetLastMessageWithAllFriends = "http://127.0.0.1:8086/messageHistory/api/v1/history/getLastMessagesWithFriendForUser?idUser=";

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Flux<LastMessageWithUser> getLastMessagesWithFriends(Mono<IdUserData> idUserDataMono) {
        return idUserDataMono.flatMapMany(idUserData -> WebClient.create().get().uri(uriMessageHistoryServiceGetLastMessageWithAllFriends + idUserData.idUser().toString())
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class)
                .flatMapMany(responseBody -> {
                    try {
                        List<LastMessageWithUser> userData = objectMapper.readValue(responseBody, new TypeReference<List<LastMessageWithUser>>() {});
                        return Flux.fromIterable(userData);
                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(JsonProcessingException.class, e -> Flux.error(new RuntimeException(e)))
        );
    }
}
