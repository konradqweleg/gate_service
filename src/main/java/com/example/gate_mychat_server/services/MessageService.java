package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.request.MessageData;
import com.example.gate_mychat_server.model.response.MessageResponse;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.in.MessageUseCase;
import com.example.gate_mychat_server.port.out.MessagePort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageService implements MessageUseCase {
    private final MessagePort messagePort;

    public MessageService(MessagePort messagePort) {
        this.messagePort = messagePort;
    }
    @Override
    public Mono<Result<Status>> insertMessage(Mono<MessageData> message) {
        return messagePort.insertMessage(message);
    }

    @Override
    public Flux<MessageResponse> getMessageBetweenUsers(Mono<IdUserData> idFirstUserMono, Mono<IdUserData> idFriendMono) {
        return messagePort.getMessageBetweenUsers(idFirstUserMono, idFriendMono);
    }
}
