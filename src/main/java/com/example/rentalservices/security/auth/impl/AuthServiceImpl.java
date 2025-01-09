package com.example.rentalservices.security.auth.impl;

import com.example.rentalservices.exception.RentalServiceApiException;
import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.mapper.CustomerMapper;
import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.Employee;
import com.example.rentalservices.model.Role;
import com.example.rentalservices.payload.NewCustomer;
import com.example.rentalservices.payload.NewEmployee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.repository.RoleRepository;
import com.example.rentalservices.security.JwtTokenProvider;
import com.example.rentalservices.security.UserAuth;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.LoginDto;
import com.example.rentalservices.validator.UserDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDataValidator userDataValidator;
    private final CustomerMapper customerMapper;


    public AuthServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider, UserDataValidator userDataValidator, CustomerMapper customerMapper, RoleRepository roleRepository) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDataValidator = userDataValidator;
        this.customerMapper = customerMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public String loginEmployee(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        Employee employee = employeeRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        logger.info("User successfully authenticated");
        return jwtTokenProvider.generateToken(authentication,new UserAuth(employee.getUuid(), employee.getRole().getName()));
    }

    @Override
    public NewEmployee registerEmployee(NewEmployee newEmployee) {
        return null;
    }

    @Override
    public String loginCustomer(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        Customer customer = customerRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(!customer.getActivationStatus()){
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"User account is not activated");
        }

        logger.info("User successfully authenticated");
        return jwtTokenProvider.generateToken(authentication,new UserAuth(customer.getUuid(), customer.getRole().getName()));
    }

    @Override
    public String registerCustomer(NewCustomer newCustomer) {
        try {
            userDataValidator.validateCustomerData(newCustomer);
            Customer customer = customerMapper.mapToCustomer(newCustomer);
            customer.setActivationStatus(false);
            customer.setUuid(UUID.randomUUID());
            customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
            customer.setRegistrationDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            customer.setRole(roleRepository.findByName("CLIENT"));
            customer = customerRepository.save(customer);

            return "Customer registered successfully";
        }catch (ValidationException e) {
        logger.error("Validation failed: {}", e.getMessage());
        throw e;
          } catch (RentalServiceApiException e) {
        logger.error("API error: {}", e.getMessage());
        throw e;
         } catch (Exception e) {
        logger.error("Unexpected error: {}", e.getMessage());
        throw new RuntimeException("Customer registration failed due to an unexpected error.", e);
    }


    }
}
