package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.UserMyChat;
import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface AuthenticationPort {
    Mono<Result<IsCorrectCredentials>> isCorrectCredentials(Mono<LoginAndPasswordData> loginAndPasswordData);
    Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData);

    Mono<Result<Status>> resendActiveUserAccountCode(Mono<IdUserData> user);

    Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user);
}
