package com.example.gate_mychat_server.port.out;

import com.example.gate_mychat_server.model.request.MessageData;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import reactor.core.publisher.Mono;

public interface MessagePort {
    Mono<Result<Status>> insertMessage(Mono<MessageData> message);
}
