package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.Role;
import com.example.gate_mychat_server.model.request.LoginAndPasswordData;
import com.example.gate_mychat_server.model.response.LoginResponse;
import com.example.gate_mychat_server.model.response.RefreshToken;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface AuthenticationUseCase {
    Mono<Result<LoginResponse>> login(Mono<LoginAndPasswordData> loginAndPasswordData);
    Mono<Result<RefreshToken>> refreshAccessToken( String userEmail,Role role);
}
