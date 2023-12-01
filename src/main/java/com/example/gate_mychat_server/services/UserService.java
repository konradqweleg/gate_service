package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.in.UserUseCase;
import com.example.gate_mychat_server.port.out.UserPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserService  implements UserUseCase {

    private final UserPort userPort;

    public UserService(UserPort userPort) {
        this.userPort = userPort;
    }

    @Override
    public Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData) {
        return userPort.register(loginAndPasswordData).flatMap(
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
    public Mono<Result<Status>> resendActiveUserAccountCode(Mono<IdUserData> user) {
        return userPort.resendActiveUserAccountCode(user).flatMap(
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
        return userPort.activateUserAccount(user).flatMap(
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
