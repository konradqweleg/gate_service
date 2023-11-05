package com.example.gate_mychat_server.adapter.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/xd")
public class ResrXdControler {


    @GetMapping("/xd")
    @PreAuthorize("hasAuthority('ACCESS_TOKEN')")
    public Mono<ResponseEntity<String>> getProfile2(Authentication authentication) {
     return    Mono.justOrEmpty(authentication)

                .filter(Objects::nonNull)

                .switchIfEmpty(Mono.error(UserException.unauthorized()))

                .map(auth ->{
                    String pric = (String) auth.getPrincipal();

                   List<GrantedAuthority> authx = (List<GrantedAuthority> )auth.getAuthorities();
                   for(GrantedAuthority x : authx){
                       System.out.println(x.getAuthority());
                   }

                    System.out.println("Proc "+pric);
                    return auth;
                    }
                )  .map(user -> {
                 return  "Ok2";
             }).map(ResponseEntity::ok);
    }

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
