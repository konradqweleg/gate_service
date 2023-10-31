package com.example.gate_mychat_server.restRequest;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import com.example.gate_mychat_server.port.out.LogInServicePort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class LogInRequest implements LogInServicePort {


    private final URI uri = new URI("http://localhost:8082/authentication/login");

    public LogInRequest() throws URISyntaxException {
    }


    @Override
    public Mono<ResponseEntity<String>>  isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData) {

        return WebClient.create().post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(loginAndPasswordData, LoginAndPasswordData.class))
                .retrieve()
                .toEntity(String.class)
                .map(responseEntity -> new ResponseEntity<>(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode()))
                .onErrorResume(response -> {
                  return Mono.just(ResponseEntity.badRequest().body(response.getMessage()));
                });
    }
}
