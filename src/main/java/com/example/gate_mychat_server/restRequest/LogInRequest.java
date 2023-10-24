package com.example.gate_mychat_server.restRequest;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import com.example.gate_mychat_server.port.out.LogInServicePort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class LogInRequest implements LogInServicePort {


    private final String uri = "http://localhost:8082/login";



    @Override
    public Mono<ResponseEntity<String>>  isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData) {

        Mono<ResponseEntity<String>> result = WebClient.create().post()
                .uri(uri)
             //   .contentType(MediaType.APPLICATION_JSON)
                .body(loginAndPasswordData, LoginAndPasswordData.class)
                .retrieve()
                .toEntity(String.class)
                .map(responseEntity -> new ResponseEntity<>(responseEntity.getBody(), responseEntity.getHeaders(), responseEntity.getStatusCode()));

        return result;
    }
}
