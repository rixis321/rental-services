package com.example.rentalservices.security.controller;

import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.LoginDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    private final AuthService authService;


    @PostMapping("/customer/register")
    public ResponseEntity<String> registerCustomer(@RequestBody NewCustomer newCustomer){
        return new ResponseEntity<>(authService.registerCustomer(newCustomer), HttpStatus.CREATED);
    }

    @PostMapping("/customer/login")
    public ResponseEntity<String> loginCustomer(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.loginCustomer(loginDto), HttpStatus.CREATED);
    }
}
