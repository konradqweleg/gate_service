package com.example.gate_mychat_server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.gate_mychat_server.model.Role;
import com.example.gate_mychat_server.model.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;

@Component
public class Tokenizer {

    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    @Value("${app.token.expires-minute}")
    private int expiresAccessTokenInMinutes;

    @Value("60")
    private int expiresRefreshTokenInMinutes;

    public String createAccessToken(String userEmail, Role role, TypeToken typeToken) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiresAccessTokenInMinutes);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withIssuer(issuer)
                .withClaim("typeToken",typeToken.getNameTypeToken())
                .withClaim("userEmail", userEmail)
                .withClaim("role", role.getNameRole())
                .withExpiresAt(expiresAt)
                .sign(algorithm());
    }

    public Mono<DecodedJWT> verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm()).withIssuer(issuer).build();
            return Mono.just(verifier.verify(token));
        } catch (Exception e) {
            return Mono.empty();
        }
    }



    public String generateRefreshToken(String email,Role role) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiresRefreshTokenInMinutes);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withSubject(email)
                .withIssuer(issuer)
                .withClaim("typeToken",TypeToken.REFRESH.getNameTypeToken())
                .withClaim("userEmail", email)
                .withClaim("role", role.getNameRole())
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
    }

    public String refreshAccessToken(String userEmail,Role role) {
        return createAccessToken(userEmail,role,TypeToken.ACCESS);
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

}