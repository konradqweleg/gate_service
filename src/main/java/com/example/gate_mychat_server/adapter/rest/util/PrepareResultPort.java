package com.example.gate_mychat_server.adapter.rest.util;

import com.example.gate_mychat_server.model.util.Result;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface PrepareResultPort {
    public <T> Mono<ResponseEntity<String>> convert (Result<T> response);
}
