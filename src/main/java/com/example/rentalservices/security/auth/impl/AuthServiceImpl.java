package com.example.rentalservices.security.auth.impl;

import com.example.rentalservices.exception.RentalServiceApiException;
import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.mapper.CustomerMapper;
import com.example.rentalservices.mapper.EmployeeMapper;
import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.Employee;
import com.example.rentalservices.model.Role;
import com.example.rentalservices.model.enums.EventType;
import com.example.rentalservices.payload.customer.NewCustomer;
import com.example.rentalservices.payload.employee.NewEmployee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.repository.RoleRepository;
import com.example.rentalservices.security.JwtTokenProvider;
import com.example.rentalservices.security.UserAuth;
import com.example.rentalservices.security.auth.AuthService;
import com.example.rentalservices.security.auth.payload.LoginDto;
import com.example.rentalservices.security.captcha.RecaptchaService;
import com.example.rentalservices.service.EventLogService;
import com.example.rentalservices.validator.PeselHandler;
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
    private final EventLogService eventLogService;
    private final PeselHandler peselHandler;
    private final EmployeeMapper employeeMapper;
    private final RecaptchaService recaptchaService;

    public AuthServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, CustomerRepository customerRepository, JwtTokenProvider jwtTokenProvider, UserDataValidator userDataValidator, CustomerMapper customerMapper, RoleRepository roleRepository, EventLogService eventLogService, PeselHandler peselHandler, EmployeeMapper employeeMapper, RecaptchaService recaptchaService) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.customerRepository = customerRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDataValidator = userDataValidator;
        this.customerMapper = customerMapper;
        this.roleRepository = roleRepository;
        this.eventLogService = eventLogService;
        this.peselHandler = peselHandler;
        this.employeeMapper = employeeMapper;
        this.recaptchaService = recaptchaService;
    }

    @Override
    public String loginEmployee(LoginDto loginDto) {
        if (!recaptchaService.verify(loginDto.getRecaptchaToken())) {
            eventLogService.logEvent(EventType.LOGIN_FAILED,"Employee login failed "+ loginDto.getEmail() + " .Invalid captcha");
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"Invalid reCAPTCHA");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        Employee employee = employeeRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);


        logger.info("Employee successfully authenticated");
        eventLogService.logEvent(EventType.LOGIN_SUCCESS,"Employee successfully" +
                " logged in",employee.getEmail());
        return jwtTokenProvider.generateToken(authentication,new UserAuth(employee.getUuid(), employee.getRole().getName()));
    }

    @Override
    public String registerEmployee(NewEmployee newEmployee) {
        try{
            userDataValidator.validateEmployeeData(newEmployee);
            Employee employee = employeeMapper.mapToEmployee(newEmployee);
            employee.setUuid(UUID.randomUUID());
            Role role = roleRepository.findByName(newEmployee.getRoleName());
            if(role == null) {
                throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"Invalid role");
            }
            employee.setRole(role);
            employee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
           employee = employeeRepository.save(employee);
            return "Employee successfully registered";

        }catch (ValidationException e) {
            logger.error("Validation failed: {}", e.getMessage());
            throw e;
        } catch (RentalServiceApiException e) {
            logger.error("API error: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
            eventLogService.logEvent(EventType.UNEXPECTED_ERROR,"Unexpected error during customer registration");
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,e.getMessage());

        }
    }
    @Override
    public String loginCustomer(LoginDto loginDto) {
        if (!recaptchaService.verify(loginDto.getRecaptchaToken())) {
            eventLogService.logEvent(EventType.LOGIN_FAILED,"Customer login failed "+ loginDto.getEmail() + " .Invalid captcha");
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"Invalid reCAPTCHA");
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        Customer customer = customerRepository.findByEmail(loginDto.getEmail()).orElseThrow(
                ()->new UsernameNotFoundException("User Not Found"));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if(!customer.getActivationStatus()){
            eventLogService.logEvent(EventType.LOGIN_FAILED,"Customer account is not activated"
                    ,customer.getEmail());
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"User account is not activated");
        }
        eventLogService.logEvent(EventType.LOGIN_SUCCESS,"Customer successfully authenticated"
                ,customer.getEmail());
        logger.info("Customer successfully authenticated");
        return jwtTokenProvider.generateToken(authentication,new UserAuth(customer.getUuid(), customer.getRole().getName()));
    }

    @Override
    public String registerCustomer(NewCustomer newCustomer) {
        if (!recaptchaService.verify(newCustomer.getRecaptchaToken())) {
            eventLogService.logEvent(EventType.REGISTRATION_FAILED,"Customer register failed "+ newCustomer.getEmail() + " .Invalid captcha");
            throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,"Invalid reCAPTCHA");
        }
        try {
            userDataValidator.validateCustomerData(newCustomer);
            if (newCustomer.getPesel() == null ) {
                throw new ValidationException("Invalid pesel number");
            }
            String decryptedPesel = peselHandler.decryptPesel(newCustomer.getPesel());
            peselHandler.isPeselValid(decryptedPesel);
            Customer customer = customerMapper.mapToCustomer(newCustomer);
            customer.setActivationStatus(false);
            customer.setPesel(peselHandler.encryptPesel(decryptedPesel));
            customer.setUuid(UUID.randomUUID());
            customer.setPassword(passwordEncoder.encode(newCustomer.getPassword()));
            customer.setRegistrationDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            customer.setRole(roleRepository.findByName("CLIENT"));


            customer = customerRepository.save(customer);

            eventLogService.logEvent(EventType.REGISTRATION_SUCCESS,"Customer successfully" +
                    " registered",customer.getEmail());
            return "Customer registered successfully";
        }catch (ValidationException e) {
        logger.error("Validation failed: {}", e.getMessage());
        throw e;
          } catch (RentalServiceApiException e) {
        logger.error("API error: {}", e.getMessage());
        throw e;
         } catch (Exception e) {
        logger.error("Unexpected error: {}", e.getMessage());
        eventLogService.logEvent(EventType.UNEXPECTED_ERROR,"Unexpected error during customer registration");
        throw new RentalServiceApiException(HttpStatus.BAD_REQUEST,e.getMessage());


        }


    }

    @Override
    public String activateCustomerAccount(LoginDto loginDto) {
        Customer customer = customerRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User Not Found"));

        customer.setActivationStatus(true);
        customer = customerRepository.save(customer);

        return "Customer account activated successfully";
    }
}
