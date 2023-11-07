package com.example.gate_mychat_server.adapter.rest;

import com.example.gate_mychat_server.adapter.rest.util.PrepareResultPort;
import com.example.gate_mychat_server.model.Role;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.port.in.AuthenticationUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/api/auth")
public class AuthRestController {

    private final AuthenticationUseCase authenticationUseCase;
    private final PrepareResultPort convertObjectToJsonResponse;

    public AuthRestController(AuthenticationUseCase authenticationUseCase, PrepareResultPort convertObjectToJsonResponse) {
        this.authenticationUseCase = authenticationUseCase;
        this.convertObjectToJsonResponse = convertObjectToJsonResponse;
    }

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> logIn(@RequestBody @Valid Mono<LoginAndPasswordData> user) {
        return authenticationUseCase.login(user).flatMap(convertObjectToJsonResponse::convert);
    }

    @PostMapping("/refreshAccessToken")
    @PreAuthorize("hasAuthority('REFRESH_TOKEN')")
    public Mono<ResponseEntity<String>> refreshToken(Authentication authentication) {
        List<GrantedAuthority> authorityList= (List<GrantedAuthority>) authentication.getAuthorities();

        String role = authorityList.get(0).getAuthority();
        String userEmail = authorityList.get(2).getAuthority();


        return authenticationUseCase.refreshAccessToken(userEmail, Role.of(role)).flatMap(convertObjectToJsonResponse::convert);
    }


}
