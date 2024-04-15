package com.example.gate_mychat_server.services;

import com.example.gate_mychat_server.model.request.IdUserData;
import com.example.gate_mychat_server.model.response.LastMessageWithUser;
import com.example.gate_mychat_server.port.in.MessageHistoryUseCase;
import com.example.gate_mychat_server.port.out.MessageHistoryPort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MessageHistoryService implements MessageHistoryUseCase {
    private final MessageHistoryPort messageHistoryPort;

    public MessageHistoryService(MessageHistoryPort messageHistoryPort) {
        this.messageHistoryPort = messageHistoryPort;
    }

    @Override
    public Flux<LastMessageWithUser> getLastMessagesWithFriends(Mono<IdUserData> idUserDataMono) {
        return messageHistoryPort.getLastMessagesWithFriends(idUserDataMono);
    }
}
