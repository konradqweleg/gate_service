package com.example.gate_mychat_server.model.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


public record UserRegisterData(@NotNull  String name, @NotNull String surname, @NotNull @Email   String email, @NotNull  String password){

}
