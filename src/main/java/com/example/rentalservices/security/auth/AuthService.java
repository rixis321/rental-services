package com.example.rentalservices.security.auth;

import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.security.auth.payload.LoginDto;

public interface AuthService {
    String loginEmployee(LoginDto loginDto);

    NewEmployee registerEmployee(NewEmployee newEmployee);

    String loginCustomer(LoginDto loginDto);

    String registerCustomer(NewCustomer newCustomer);


}
