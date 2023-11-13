package com.example.gate_mychat_server.restRequest;

import com.example.gate_mychat_server.model.UserMyChat;
import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.out.AuthenticationPort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class AuthRequest implements AuthenticationPort {

    private final ObjectMapper objectMapper ;
    private final URI uriLogin = new URI("http://localhost:8082/authentication/login");
    private final URI uriRegister = new URI("http://localhost:8082/authentication/register");
    private final URI uriResendActiveUserAccountCode = new URI("http://localhost:8082/activeAccount/resendCode");
    private final URI uriActiveUserAccount = new URI("http://localhost:8082/activeAccount");


    public AuthRequest(ObjectMapper objectMapper) throws URISyntaxException {
        this.objectMapper = objectMapper;
    }


    @Override
    public Mono<Result<IsCorrectCredentials>> isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData) {

        return WebClient.create().post()
                .uri(uriLogin)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(loginAndPasswordData, LoginAndPasswordData.class))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        IsCorrectCredentials isCorrectCredentials =  objectMapper.readValue(responseEntity.getBody(), IsCorrectCredentials.class);
                        return Mono.just(Result.success(isCorrectCredentials));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<IsCorrectCredentials>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> register(Mono<UserRegisterData> userRegisterData) {
        return WebClient.create().post().uri(uriRegister)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(userRegisterData, UserRegisterData.class))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> resendActiveUserAccountCode(Mono<IdUserData> user) {
        return WebClient.create().post().uri(uriResendActiveUserAccountCode).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(user, IdUserData.class))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user) {
        return WebClient.create().post().uri(uriActiveUserAccount).contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(user, ActiveAccountCodeData.class))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }
}
