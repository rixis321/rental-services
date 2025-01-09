package com.example.rentalservices.payload;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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
}
