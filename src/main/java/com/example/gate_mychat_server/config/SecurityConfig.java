package com.example.gate_mychat_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
@EnableReactiveMethodSecurity
public class SecurityConfig {

    private static final String[] PUBLIC = {
            "/api/v1/users/register", //register
            "/api/v1/users/login", //login
            "/api/v1/user/test",
            "/api/v1/auth/email",
            "/api/auth/refreshAccessToken","/api/v1/user/register",
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

    private final TokenSecurityContextRepository securityContextRepository;
    private final JwtAuthFilter jwtAuthenticationFilter;

    public SecurityConfig(TokenSecurityContextRepository securityContextRepository, JwtAuthFilter jwtAuthenticationFilter) {
        this.securityContextRepository = securityContextRepository;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .securityContextRepository(securityContextRepository)
                .addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchange -> exchange
                        .pathMatchers(PUBLIC).permitAll()
                        .anyExchange().authenticated())
                .build();
    }

}