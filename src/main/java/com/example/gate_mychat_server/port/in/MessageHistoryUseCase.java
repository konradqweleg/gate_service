package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.LastMessageWithUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageHistoryUseCase {
    Flux<LastMessageWithUser> getLastMessagesWithFriends(Mono<IdUserData> idUserDataMono);
}
