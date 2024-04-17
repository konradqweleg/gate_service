package com.example.gate_mychat_server.port.in;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.MessageData;
import com.example.gate_mychat_server.model.response.MessageResponse;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MessageUseCase {
    Mono<Result<Status>> insertMessage(Mono<MessageData> message);
    Flux<MessageResponse> getMessageBetweenUsers(Mono<IdUserData> idFirstUserMono, Mono<IdUserData> idFriendMono);
}
