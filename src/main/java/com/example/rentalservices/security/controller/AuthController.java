package com.example.rentalservices.security.controller;

import com.example.rentalservices.payload.customer.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.JwtAuthResponse;
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
        String token = authService.loginCustomer(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok()
                .body("Bearer "+ jwtAuthResponse.getAccessToken());

    }

    @PostMapping("/employee/register")
    public ResponseEntity<String> registerEmployee(@RequestBody NewEmployee newEmployee){
        return new ResponseEntity<>(authService.registerEmployee(newEmployee), HttpStatus.CREATED);
    }

    @PostMapping("/employee/login")
    public ResponseEntity<String> loginEmployee(@RequestBody LoginDto loginDto){
        String token = authService.loginEmployee(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok()
                .body("Bearer "+ jwtAuthResponse.getAccessToken());

    }

    @PutMapping("/customer/activate")
    public ResponseEntity<String> activateCustomerAccount(@RequestBody LoginDto loginDto){
        return new ResponseEntity<>(authService.activateCustomerAccount(loginDto), HttpStatus.OK);
    }


}
