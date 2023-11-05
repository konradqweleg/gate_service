package com.example.gate_mychat_server.restRequest;

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
public class LogInRequest implements AuthenticationPort {

    private final ObjectMapper objectMapper ;
    private final URI uri = new URI("http://localhost:8082/authentication/login");

    public LogInRequest(ObjectMapper objectMapper) throws URISyntaxException {
        this.objectMapper = objectMapper;
    }


    @Override
    public Mono<Result<IsCorrectCredentials>>  isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData) {

        return WebClient.create().post()
                .uri(uri)
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
}
