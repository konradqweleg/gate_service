package com.example.gate_mychat_server.adapter.rest;

import com.example.gate_mychat_server.config.Tokenizer;
import com.example.gate_mychat_server.model.LoginAndPasswordData;
import com.example.gate_mychat_server.port.in.LogInUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/login")
public class RestController {

    private final LogInUseCase logInUseCase;
    private final Tokenizer tokenizer;

    public RestController(LogInUseCase logInUseCase, Tokenizer tokenizer) {
        this.logInUseCase = logInUseCase;
        this.tokenizer = tokenizer;
    }


//    @PostMapping("login")
//    public Mono<ResponseEntity<LoginResponse>> login(@RequestBody LoginRequest request) {
//        // start with find requested email in DB
//        return userRepository.findByEmail(request.getEmail())
//
//                // match password
//                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
//
//                // transform to user id
//                .map(User::getId)
//
//                // map as desired spec and generate token (JWT)
//                .map(userId -> {
//                    LoginResponse response = new LoginResponse();
//                    response.setToken(tokenizer.tokenize(Long.toString(userId)));
//                    return ResponseEntity.ok(response);
//                })
//
//                // fail to log in? mark as unauthorized.
//                .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
//    }




    @PostMapping
    public Mono<ResponseEntity<String>> logIn(@RequestBody @Valid Mono<LoginAndPasswordData> user) {

        return logInUseCase.login(user).map(
                x->{
                        String token = tokenizer.tokenize("example");
                        return ResponseEntity.ok(token);
                }

        );
    }


}
