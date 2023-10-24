package com.example.gate_mychat_server.adapter.rest;

import com.example.gate_mychat_server.model.LoginAndPasswordData;
import com.example.gate_mychat_server.port.in.LogInUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/login")
public class RestController {

    private final LogInUseCase logInUseCase;

    public RestController(LogInUseCase logInUseCase) {
        this.logInUseCase = logInUseCase;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> logIn(@RequestBody @Valid Mono<LoginAndPasswordData> user) {
        return logInUseCase.login(user);
    }
}
