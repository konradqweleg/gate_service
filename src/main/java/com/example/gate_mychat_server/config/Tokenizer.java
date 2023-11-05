package com.example.gate_mychat_server.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Component
public class Tokenizer {

    @Value("${app.token.secret}")
    private String secret;

    @Value("${app.token.issuer}")
    private String issuer;

    @Value("${app.token.expires-minute}")
    private int expires;

    public String tokenize(String userEmail) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expires);
        Date expiresAt = calendar.getTime();

        return JWT.create()

                .withIssuer(issuer)
                .withClaim("TYPE_TOKEN","ACCESS_TOKEN")
                .withClaim("principal", userEmail)

                .withClaim("role", "USER")

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

    public Optional<DecodedJWT> verifyBlocked(String token) {
        try {
           // Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm()).withIssuer(issuer).build();
            return Optional.of(verifier.verify(token));
         //   return Optional.of( verifier.verify(token));
        } catch (Exception e) {
            System.out.println(e);
            return Optional.empty();
        }
    }

    public String generateRefreshToken(String userId) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 100);
        Date expiresAt = calendar.getTime();

        return JWT.create()
                .withSubject(userId)
                .withIssuer(issuer)
                .withClaim("role", "USER")
                .withClaim("TYPE_TOKEN","REFRESH_TOKEN")
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
    }

    public String refreshAccessToken(String refreshToken) {
        DecodedJWT decodedRefreshToken = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer(issuer)
                .build()
                .verify(refreshToken);



        String userEmail = decodedRefreshToken.getSubject();

        return tokenize(userEmail);
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(secret);
    }

}