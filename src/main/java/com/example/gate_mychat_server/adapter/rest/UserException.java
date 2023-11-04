package com.example.gate_mychat_server.adapter.rest;

public class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }

    public static UserException unauthorized() {
        return new UserException("Unauthorized access");
    }
}

