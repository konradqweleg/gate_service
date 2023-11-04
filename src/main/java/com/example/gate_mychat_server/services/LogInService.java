package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import com.example.gate_mychat_server.port.in.LogInUseCase;
import com.example.gate_mychat_server.port.out.LogInServicePort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class LogInService implements LogInUseCase {
    LogInServicePort logInServicePort;

    public LogInService(LogInServicePort logInServicePort) {
        this.logInServicePort = logInServicePort;
    }

    @Override
    public Mono<ResponseEntity<String>> login(Mono<LoginAndPasswordData> loginAndPasswordData) {
        return logInServicePort.isCorrectCredentials(loginAndPasswordData);
    }






}
