package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.LastMessageWithUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageHistoryPort {
    Flux<LastMessageWithUser> getLastMessagesWithFriends(Mono<IdUserData> idUserDataMono);
}
