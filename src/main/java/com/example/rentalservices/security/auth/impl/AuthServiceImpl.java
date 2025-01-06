package com.example.rentalservices.security.auth.impl;

import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.LoginDto;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {


    @Override
    public String loginEmployee(LoginDto loginDto) {
        return "";
    }

    @Override
    public NewEmployee registerEmployee(NewEmployee newEmployee) {
        return null;
    }

    @Override
    public String loginCustomer(LoginDto loginDto) {
        return "";
    }

    @Override
    public NewCustomer registerCustomer(NewCustomer newCustomer) {
        return null;
    }
}
