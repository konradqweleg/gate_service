package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.response.IsCorrectCredentials;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface AuthenticationPort {
    Mono<Result<IsCorrectCredentials>> login(Mono<LoginAndPasswordData> loginAndPasswordData);

}
