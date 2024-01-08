package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.ActiveAccountCodeData;
import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.UserEmailData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface UserPort {
     Mono<Result<Status>> register(Mono<UserRegisterData> loginAndPasswordData);
     Mono<Result<Status>> resendActiveUserAccountCode(Mono<UserEmailData> user);

    Mono<Result<Status>> activateUserAccount(Mono<ActiveAccountCodeData> user);
}
