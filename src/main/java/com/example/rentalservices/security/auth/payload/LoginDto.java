package com.example.rentalservices.security.auth.payload;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class LoginDto {
    private String password;
    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
