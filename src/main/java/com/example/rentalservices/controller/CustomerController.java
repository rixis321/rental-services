package com.example.rentalservices.controller;

import com.example.rentalservices.payload.customer.ShortCustomerDto;
import com.example.rentalservices.security.CustomUserDetailsService;
import com.example.rentalservices.security.auth.AccessControlService;
import com.example.rentalservices.service.CustomerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
public class CustomerController {
    private final CustomerService customerService;
    private final CustomUserDetailsService customUserDetailsService;
    private final AccessControlService accessControlService;
    public CustomerController(CustomerService customerService, CustomUserDetailsService customUserDetailsService, AccessControlService accessControlService) {
        this.customerService = customerService;
        this.customUserDetailsService = customUserDetailsService;
        this.accessControlService = accessControlService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/customers")
    public ResponseEntity<List<ShortCustomerDto>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

//TODO ZEMINIC SHORT NA -> CUSTOMERDTO
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ShortCustomerDto> getCustomerById(@PathVariable UUID customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (accessControlService.isAdmin(userDetails)) {
            return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
        } else if (accessControlService.isClientWithAccess(userDetails, customerId)) {
            return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/customers/{customerID}/pesel")
    public ResponseEntity<String> getCustomerPeselNumber(@PathVariable("customerID") UUID customerID) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (accessControlService.isAdmin(userDetails)) {
            return new ResponseEntity<>(customerService.getCustomerPeselNumber(customerID), HttpStatus.OK);
        } else if (accessControlService.isClientWithAccess(userDetails, customerID)) {
            return new ResponseEntity<>(customerService.getCustomerPeselNumber(customerID), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

    }


}
