package com.example.gate_mychat_server.model.request;

import jakarta.validation.constraints.NotNull;

public record LoginAndPasswordData(@NotNull String email, @NotNull String password) {
}
