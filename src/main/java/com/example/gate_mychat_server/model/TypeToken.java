package com.example.gate_mychat_server.model;

public enum TypeToken {
   ACCESS("ACCESS_TOKEN"),
   REFRESH("REFRESH_TOKEN");

    public String getNameTypeToken() {
        return nameTypeToken;
    }
    private final String nameTypeToken;

    TypeToken(String nameTypeToken) {
        this.nameTypeToken = nameTypeToken;
    }
}
