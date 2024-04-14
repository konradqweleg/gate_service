package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.UserData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendUseCase {
    Flux<UserData> getUserFriends(Mono<IdUserData> idUserMono);
}
