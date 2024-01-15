package com.example.gate_mychat_server.error;

public enum ErrorMessage {
    BAD_CREDENTIALS("Bad credentials"),
    ERROR("Respomse unavailable");

    private String message;

    public String getMessage(){
        return message;
    }

    public String getFullJSON(){
        return "{\"ErrorMessage\" : \""+message+"\"}";
    }
    ErrorMessage(String message) {
        this.message = message;
    }
}

