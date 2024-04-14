package com.example.gate_mychat_server.adapter.out.friend_service;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.port.out.FriendsPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FriendServiceAdapter implements FriendsPort {
    private final String uriGetUserFriends = "http://127.0.0.1:8084/friendsService/api/v1/friends/getFriends?idUser=";

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Flux<UserData> getUserFriends(Mono<IdUserData> idUserMono) {
        return idUserMono.flatMapMany(idUserData -> WebClient.create().get().uri(uriGetUserFriends + idUserData.idUser().toString())
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(Exception::new)
                )
                .bodyToMono(String.class)
                .flatMapMany(responseBody -> {
                    try {
                        List<UserData> userData = objectMapper.readValue(responseBody, new TypeReference<List<UserData>>() {});
                        return Flux.fromIterable(userData);
                    } catch (JsonProcessingException e) {
                        return Flux.error(new RuntimeException(e));
                    }
                })
                .onErrorResume(JsonProcessingException.class, e -> Flux.error(new RuntimeException(e)))
        );
    }
}
