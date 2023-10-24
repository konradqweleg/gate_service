package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface LogInUseCase {
    Mono<ResponseEntity<String>> login(Mono<LoginAndPasswordData> loginAndPasswordData);
}
