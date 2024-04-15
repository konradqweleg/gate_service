package com.example.gate_mychat_server.model.request;

public record MessageData(String message, Long id_user_sender, Long id_user_receiver) {
}
