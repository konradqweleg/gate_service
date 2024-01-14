package com.example.gate_mychat_server.model.request;

import jakarta.validation.constraints.NotNull;

public record ChangePasswordData(@NotNull String email, @NotNull String code, @NotNull String password) {
}
