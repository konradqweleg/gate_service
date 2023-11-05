package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.config.Tokenizer;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.response.LoginResponse;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.in.AuthenticationUseCase;
import com.example.gate_mychat_server.port.out.AuthenticationPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService implements AuthenticationUseCase {
    AuthenticationPort authenticationPort;
    private final Tokenizer tokenizer;

    private static final String ERROR = "Bad credentials";

    public AuthenticationService(AuthenticationPort authenticationPort, Tokenizer tokenizer) {
        this.authenticationPort = authenticationPort;
        this.tokenizer = tokenizer;
    }

    @Override
    public Mono<Result<LoginResponse>> login(Mono<LoginAndPasswordData> loginAndPasswordData) {
        Mono<LoginAndPasswordData> cacheLoginAndPassword = loginAndPasswordData.cache();

        return authenticationPort.isCorrectCredentials(cacheLoginAndPassword).filter(
                        isCorrectCredentialsResult -> isCorrectCredentialsResult.isSuccess()
                                && isCorrectCredentialsResult.getValue().isCorrectCredentials()
                ).
                flatMap(correctCredentialsResult -> cacheLoginAndPassword).
                flatMap(
                        loginAndPasswordAuthorizedUser -> {
                            String token = tokenizer.tokenize(loginAndPasswordAuthorizedUser.login());
                            String refreshToken = tokenizer.generateRefreshToken(loginAndPasswordAuthorizedUser.login());
                            LoginResponse loginResponse = new LoginResponse(token, refreshToken);
                            return Mono.just(Result.<LoginResponse>success(loginResponse));
                        }
                ).switchIfEmpty(Mono.just(Result.<LoginResponse>error(ERROR)))
                .onErrorResume(
                        response -> Mono.just(Result.<LoginResponse>error(ERROR)));

    }


}
