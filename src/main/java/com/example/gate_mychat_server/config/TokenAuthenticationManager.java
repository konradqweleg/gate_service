package com.example.gate_mychat_server.config;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Component
public class TokenAuthenticationManager implements ReactiveAuthenticationManager {

    private final Tokenizer tokenizer;

    public TokenAuthenticationManager(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication.getCredentials())

                .filter(Objects::nonNull)

                .flatMap((Function<Object, Mono<DecodedJWT>>) credential -> tokenizer.verify((String) credential))

                .flatMap((Function<DecodedJWT, Mono<UsernamePasswordAuthenticationToken>>) decodedJWT -> {

                    String role = decodedJWT.getClaim("role").asString();
                    String kindOfToken = decodedJWT.getClaim("typeToken").asString();
                    String userEmail = decodedJWT.getClaim("userEmail").asString();

                    List<SimpleGrantedAuthority> authorities = List.of(
                            new SimpleGrantedAuthority(role),
                            new SimpleGrantedAuthority(kindOfToken),
                            new SimpleGrantedAuthority(userEmail)
                    );

                    return Mono.just(new UsernamePasswordAuthenticationToken(userEmail, null, authorities));
                });
    }

}
