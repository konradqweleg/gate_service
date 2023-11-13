package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.config.Tokenizer;
import com.example.gate_mychat_server.model.Role;
import com.example.gate_mychat_server.model.TypeToken;
import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.response.LoginResponse;
import com.example.gate_mychat_server.model.response.RefreshToken;
import com.example.gate_mychat_server.model.response.Status;
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
                            String token = tokenizer.createAccessToken(loginAndPasswordAuthorizedUser.login(), Role.USER, TypeToken.ACCESS);
                            String refreshToken = tokenizer.generateRefreshToken(loginAndPasswordAuthorizedUser.login(), Role.USER);
                            LoginResponse loginResponse = new LoginResponse(token, refreshToken);
                            return Mono.just(Result.<LoginResponse>success(loginResponse));
                        }
                ).switchIfEmpty(Mono.just(Result.<LoginResponse>error(ERROR)))
                .onErrorResume(
                        response -> Mono.just(Result.<LoginResponse>error(ERROR)));

    }

    @Override
    public Mono<Result<RefreshToken>> refreshAccessToken(String userEmail, Role role) {
        String generatedAccessToken = tokenizer.createAccessToken(userEmail, role, TypeToken.ACCESS);
        return Mono.just(Result.success(new RefreshToken(generatedAccessToken)));

    }

    @Override
    public Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData) {
        return authenticationPort.register(loginAndPasswordData).flatMap(
                statusResult -> {
                    if (statusResult.isSuccess()) {
                        return Mono.just(Result.success(new Status(statusResult.getValue().correctResponse())));
                    } else {
                        return Mono.just(Result.<Status>error(statusResult.getError()));
                    }
                }
        ).onErrorResume(
                response -> Mono.just(Result.<Status>error(response.getMessage())));


    }

    @Override
    public Mono<Result<IsCorrectCredentials>> isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData) {
        return authenticationPort.isCorrectCredentials(loginAndPasswordData).flatMap(
                isCorrectCredentialsResult -> {
                    if (isCorrectCredentialsResult.isSuccess()) {
                        return Mono.just(Result.success(new IsCorrectCredentials(isCorrectCredentialsResult.getValue().isCorrectCredentials())));
                    } else {
                        return Mono.just(Result.<IsCorrectCredentials>error(isCorrectCredentialsResult.getError()));
                    }
                }
        ).onErrorResume(
                response -> Mono.just(Result.<IsCorrectCredentials>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> resendActiveUserAccountCode(Mono<IdUserData> user) {
        return authenticationPort.resendActiveUserAccountCode(user).flatMap(
                statusResult -> {
                    if (statusResult.isSuccess()) {
                        return Mono.just(Result.success(new Status(statusResult.getValue().correctResponse())));
                    } else {
                        return Mono.just(Result.<Status>error(statusResult.getError()));
                    }
                }
        ).onErrorResume(
                response -> Mono.just(Result.<Status>error(response.getMessage())));
    }

    @Override
    public Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user) {
        return authenticationPort.activateUserAccount(user).flatMap(
                statusResult -> {
                    if (statusResult.isSuccess()) {
                        return Mono.just(Result.success(new Status(statusResult.getValue().correctResponse())));
                    } else {
                        return Mono.just(Result.<Status>error(statusResult.getError()));
                    }
                }
        ).onErrorResume(
                response -> Mono.just(Result.<Status>error(response.getMessage())));
    }


}
