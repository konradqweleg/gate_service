package com.example.gate_mychat_server.adapter.out.friend_service;

import com.example.gate_mychat_server.model.request.FriendsIdsData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.response.UserData;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.out.FriendsPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class FriendServiceAdapter implements FriendsPort {
    private final String uriGetUserFriends = "http://127.0.0.1:8084/friendsService/api/v1/friends/getFriends?idUser=";
    private final String uriCreateFriends = "http://127.0.0.1:8084/friendsService/api/v1/friends/createFriends";

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

    @Override
    public Mono<Result<Status>> createFriends(Mono<FriendsIdsData> friendsIdsMono) {
        return friendsIdsMono.flatMap(friendsIdsData -> WebClient.create().post().uri(uriCreateFriends)
                .body(Mono.just(friendsIdsData), FriendsIdsData.class)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        clientResponse -> clientResponse.bodyToMono(String.class).map(message -> new WebClientResponseException(message, 400, "Bad Request", null, null, null))
                )
                .bodyToMono(String.class)
                .flatMap(responseBody -> {
                    System.out.println(responseBody);
                    if(responseBody.contains("User not found")) {
                        return Mono.just(Result.success(new Status(false)));
                    }

                    if (responseBody.contains("Friend already exists")) {
                        return Mono.just(Result.success(new Status(false)));
                    }

                    try {
                        Status status = objectMapper.readValue(responseBody, Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException("Error processing JSON: " + e.getMessage(), e));
                    }
                })
                .onErrorResume(e -> Mono.just(Result.success(new Status(false))))
        );
    }
}
