package com.example.rentalservices.security.auth.impl;

import com.example.rentalservices.model.Employee;
import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.security.JwtTokenProvider;
import com.example.rentalservices.security.UserAuth;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.LoginDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String loginEmployee(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        Employee employee = employeeRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //TODO


        return jwtTokenProvider.generateToken(authentication,new UserAuth(employee.getUuid(), employee.getRole().getName()));
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
