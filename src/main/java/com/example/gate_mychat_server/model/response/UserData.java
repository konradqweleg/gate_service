package com.example.gate_mychat_server.model.response;

import jakarta.validation.constraints.NotNull;


public record UserData(Long id, @NotNull String name, @NotNull String surname, @NotNull String email) {
}
