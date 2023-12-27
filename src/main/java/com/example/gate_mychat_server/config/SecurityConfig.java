package com.example.gate_mychat_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC = {"/api/v1/auth/login", "/api/auth/refreshAccessToken","/api/v1/user/register", "/api/v1/user/resendActiveAccountCode","/api/v1/user/activeUserAccount"};

    private final TokenSecurityContextRepository securityContextRepository;

    public SecurityConfig(TokenSecurityContextRepository securityContextRepository) {
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)

                .formLogin().disable()

                .httpBasic().disable()

                .securityContextRepository(securityContextRepository)

                .authorizeExchange().pathMatchers(PUBLIC).permitAll().anyExchange().authenticated()

                .and().build();
    }

}