package com.example.rentalservices.payload.customer;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
public class NewCustomer {

    @NotEmpty(message = "Name should not be null or empty")
    private String firstName;
    @NotEmpty(message = "lastname should not be null or empty")
    private String lastName;
    @NotEmpty(message = "email should not be null or empty")
    private String email;
    @NotEmpty(message = "phone number should not be null or empty")
    private String phone;
    private String pesel;
    @NotEmpty(message = "password should not be null or empty")
    private String password;
    public @NotEmpty(message = "Name should not be null or empty") String getFirstName() {
        return firstName;
    }
    private String recaptchaToken;
    public void setFirstName(@NotEmpty(message = "Name should not be null or empty") String firstName) {
        this.firstName = firstName;
    }

    public @NotEmpty(message = "lastname should not be null or empty") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotEmpty(message = "lastname should not be null or empty") String lastName) {
        this.lastName = lastName;
    }

    public @NotEmpty(message = "email should not be null or empty") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "email should not be null or empty") String email) {
        this.email = email;
    }

    public @NotEmpty(message = "phone number should not be null or empty") String getPhone() {
        return phone;
    }

    public void setPhone(@NotEmpty(message = "phone number should not be null or empty") String phone) {
        this.phone = phone;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public @NotEmpty(message = "password should not be null or empty") String getPassword() {
        return password;
    }

    public void setPassword(@NotEmpty(message = "password should not be null or empty") String password) {
        this.password = password;
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
        NewCustomer that = (NewCustomer) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(pesel, that.pesel) && Objects.equals(password, that.password) && Objects.equals(recaptchaToken, that.recaptchaToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phone, pesel, password, recaptchaToken);
    }

    @Override
    public String toString() {
        return "NewCustomer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", pesel='" + pesel + '\'' +
                ", password='" + password + '\'' +
                ", recaptchaToken='" + recaptchaToken + '\'' +
                '}';
    }
}
