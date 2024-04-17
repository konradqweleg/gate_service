package com.example.gate_mychat_server.model.response;

import java.sql.Timestamp;

public record MessageResponse(Long idFriend, Long idSender, Long idReceiver, String message, Long idMessage, Timestamp dateTimeMessage) {
}
