package com.example.gate_mychat_server.model;

import jakarta.validation.constraints.NotNull;


public record UserMyChat( Long id, @NotNull String name, @NotNull String surname, @NotNull String email, @NotNull String password, @NotNull int idRole, @NotNull boolean isActiveAccount) {

}
