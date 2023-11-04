package com.example.gate_mychat_server.adapter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/xd")
public class ResrXdControler {


    @GetMapping
    public Mono<ResponseEntity<String>> getProfile(Authentication authentication) {
        System.out.println(authentication);
        return Mono.justOrEmpty(authentication)

                .filter(Objects::nonNull)

                .switchIfEmpty(Mono.error(UserException.unauthorized()))

                .map(auth -> (String) auth.getPrincipal())

                .flatMap((Function<String, Mono<String>>) userId -> Mono.just("Ok") )

                .map(user -> {
                    return  "Ok2";
                })

                .switchIfEmpty(Mono.error(UserException.unauthorized()))

                .map(ResponseEntity::ok);
    }
}
