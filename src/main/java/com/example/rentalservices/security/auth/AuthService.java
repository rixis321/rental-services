package com.example.rentalservices.security.auth;

import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.security.auth.payload.LoginDto;

import java.util.UUID;

public interface AuthService {
    String loginEmployee(LoginDto loginDto);

    String registerEmployee(NewEmployee newEmployee);

    String loginCustomer(LoginDto loginDto);

    String registerCustomer(NewCustomer newCustomer);

    String activateCustomerAccount(LoginDto loginDto);

}
