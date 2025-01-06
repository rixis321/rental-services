package com.example.rentalservices.security;

import java.util.UUID;

public class UserAuth {
    private UUID uuid;
    private String role;

    public UserAuth(UUID uuid, String role) {
        this.uuid = uuid;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
