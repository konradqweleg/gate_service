package com.example.gate_mychat_server.adapter.out.auth;


import com.example.gate_mychat_server.error.ErrorMessage;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
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
    private final URI uriLogin = new URI("http://localhost:8082/userServices/api/v1/user/login");



    public AuthRequest(ObjectMapper objectMapper) throws URISyntaxException {
        this.objectMapper = objectMapper;
    }


    @Override
    public Mono<Result<IsCorrectCredentials>> login(Mono<LoginAndPasswordData> loginAndPasswordData) {

        return WebClient.create().post()
                .uri(uriLogin)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(loginAndPasswordData, LoginAndPasswordData.class))
                .retrieve()
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        IsCorrectCredentials isCorrectCredentials =  objectMapper.readValue(responseEntity.getBody(), IsCorrectCredentials.class);

                        if(isCorrectCredentials.isCorrectCredentials())
                            return Mono.just(Result.success(isCorrectCredentials));
                        else
                            return Mono.just(Result.<IsCorrectCredentials>error(ErrorMessage.BAD_CREDENTIALS.getMessage()));


                    } catch (JsonProcessingException e) {
                        return Mono.just(Result.<IsCorrectCredentials>error(ErrorMessage.ERROR.getMessage()));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<IsCorrectCredentials>error(ErrorMessage.ERROR.getMessage())));
    }


}
