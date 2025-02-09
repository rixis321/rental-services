package com.example.rentalservices.security.auth.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


@NoArgsConstructor
public class LoginDto {
    private String password;
    private String email;
    private String recaptchaToken;
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

    public String getRecaptchaToken() {
        return recaptchaToken;
    }

    public void setRecaptchaToken(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginDto loginDto = (LoginDto) o;
        return Objects.equals(password, loginDto.password) && Objects.equals(email, loginDto.email) && Objects.equals(recaptchaToken, loginDto.recaptchaToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password, email, recaptchaToken);
    }

    @Override
    public String toString() {
        return "LoginDto{" +
                "password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", recaptchaToken='" + recaptchaToken + '\'' +
                '}';
    }
}
