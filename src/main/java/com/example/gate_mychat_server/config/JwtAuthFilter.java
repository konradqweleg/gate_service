package com.example.gate_mychat_server.config;

import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.rotation.AdapterTokenVerifier;
import org.keycloak.common.VerificationException;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements WebFilter {

    private final String realm = "MyChatApp";
    private final String client = "admin-cli";
    private final String authServerUrl = "http://localhost:8080";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            try {
                AccessToken accessToken = parseToken(token);
                SecurityContextHolder.getContext().setAuthentication(new JWTAuthToken(accessToken));
            } catch (VerificationException e) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        }
        return chain.filter(exchange);
    }

    private AccessToken parseToken(String token) throws VerificationException {
        AdapterConfig config = new AdapterConfig();
        config.setRealm(realm);
        config.setResource(client);
        config.setAuthServerUrl(authServerUrl);
        KeycloakDeployment deployment = KeycloakDeploymentBuilder.build(config);
        return AdapterTokenVerifier.verifyToken(token, deployment);
    }
}