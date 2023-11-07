package com.example.gate_mychat_server.model;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");
    private final String nameRole;


    public static Role of(String nameRole){
        return switch (nameRole) {
            case "USER" -> USER;
            case "ADMIN" -> ADMIN;
            default -> throw new IllegalStateException("Unexpected value: " + nameRole);
        };
    }
    public String getNameRole() {
        return nameRole;
    }
    Role(String nameRole) {
        this.nameRole = nameRole;
    }
}
