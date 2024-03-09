package com.example.gate_mychat_server.model.response;

import java.sql.Timestamp;

public record LastMessageWithUser(Long idFriend,Long idSender, Long idReceiver, Long idMessage, String name, String surname, String message, Timestamp dateTimeMessage) {
}
