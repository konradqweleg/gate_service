package com.example.gate_mychat_server.adapter.out.message_service_adapter;

import com.example.gate_mychat_server.model.request.MessageData;
import com.example.gate_mychat_server.model.request.UserRegisterData;
import com.example.gate_mychat_server.model.response.Status;
import com.example.gate_mychat_server.model.util.Result;
import com.example.gate_mychat_server.port.out.MessagePort;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MessageServiceAdapter implements MessagePort {

    private final String uriSendMessage = "http://127.0.0.1:8085/messageService/api/v1/message/insertMessage";

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public Mono<Result<Status>> insertMessage(Mono<MessageData> message) {
        return WebClient.create().post().uri(uriSendMessage)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(message, MessageData.class))
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        response -> response.bodyToMono(String.class).map(Exception::new)
                )
                .toEntity(String.class)
                .flatMap(responseEntity -> {
                    try {
                        System.out.println(responseEntity.getBody());
                        Status status =  objectMapper.readValue(responseEntity.getBody(), Status.class);
                        return Mono.just(Result.success(status));
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException(e));
                    }

                })
                .onErrorResume(response -> Mono.just(Result.<Status>error(response.getMessage())));
    }
}
