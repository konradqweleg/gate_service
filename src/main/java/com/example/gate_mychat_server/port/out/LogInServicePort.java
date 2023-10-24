package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface LogInServicePort {
    Mono<ResponseEntity<String>>  isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData);
}
