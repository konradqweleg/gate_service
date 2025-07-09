package com.example.gate_mychat_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;

import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    private static final String[] PUBLIC = {
            "/api/v1/users/register",
            "/api/v1/users/login",
            "/api/v1/users/refresh-token",

            "/api/v1/user/test",
            "/api/v1/auth/email",

            "/api/auth/refreshAccessToken",
            "/api/v1/user/register",
            "/api/v1/users/activate",
            "/api/v1/user/resendActiveUserAccountCode",
            "/api/v1/user/activeUserAccount",
            "/api/v1/user/checkIsUserWithThisEmailExist",
            "/api/v1/user/sendResetPasswordCode",
            "/api/v1/user/checkIsCorrectResetPasswordCode",
            "/api/v1/user/changeUserPassword",
            "/api/v1/user/validateToken",
            "/api/v1/auth/login",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/swagger-ui/index.html",
            "/webjars/swagger-ui/**",
            "/v1/user/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(PUBLIC).permitAll()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))

                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((exchange, ex) -> {
                            exchange.getResponse().setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        })
                )
                .oauth2Login(Customizer.withDefaults())
                .build();

    }


}