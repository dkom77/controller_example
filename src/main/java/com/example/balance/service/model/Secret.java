package com.example.balance.service.model;

public class Secret {
    private String passwordHash;
    private String salt;

    public Secret(String passwordHash, String salt) {
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }
}
