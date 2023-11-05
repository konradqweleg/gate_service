package com.example.gate_mychat_server.adapter.rest;

import com.example.gate_mychat_server.adapter.rest.util.PrepareResultPort;
import com.example.gate_mychat_server.config.Tokenizer;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.port.in.AuthenticationUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/login")
public class RestController {

    private final AuthenticationUseCase authenticationUseCase;
    private final PrepareResultPort convertObjectToJsonResponse;

    public RestController(AuthenticationUseCase authenticationUseCase, PrepareResultPort convertObjectToJsonResponse) {
        this.authenticationUseCase = authenticationUseCase;
        this.convertObjectToJsonResponse = convertObjectToJsonResponse;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> logIn(@RequestBody @Valid Mono<LoginAndPasswordData> user) {
        return authenticationUseCase.login(user).flatMap(convertObjectToJsonResponse::convert);
    }


}
