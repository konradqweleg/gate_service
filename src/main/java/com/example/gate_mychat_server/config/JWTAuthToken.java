package com.example.gate_mychat_server.config;

import org.keycloak.representations.AccessToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
public class JWTAuthToken extends AbstractAuthenticationToken {

    private final AccessToken accessToken;

    public JWTAuthToken(AccessToken accessToken) {
        super(null);
        this.accessToken = accessToken;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return accessToken;
    }
}