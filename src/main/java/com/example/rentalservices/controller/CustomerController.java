package com.example.rentalservices.controller;

import com.example.rentalservices.payload.ShortCustomerDto;
import com.example.rentalservices.security.CustomUserDetailsService;
import com.example.rentalservices.service.CustomerService;
import com.example.rentalservices.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
    private final UserService userService;
    public CustomerController(CustomerService customerService, CustomUserDetailsService customUserDetailsService, UserService userService) {
        this.customerService = customerService;
        this.customUserDetailsService = customUserDetailsService;
        this.userService = userService;
    }


    @GetMapping("/customers")
    public ResponseEntity<List<ShortCustomerDto>> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }


    @GetMapping("/customers/{customerId}")
    public ResponseEntity<ShortCustomerDto> getCustomerById(@PathVariable UUID customerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))){
            return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
        }
        else if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("CLIENT"))) {
            if(userService.doesUserExistByEmailAndId(userDetails.getUsername(), customerId)){
                return new ResponseEntity<>(customerService.getCustomer(customerId), HttpStatus.OK);
            }else
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        else
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/customers/{customerID}/pesel")
    public ResponseEntity<String> getCustomerPeselNumber(@PathVariable("customerID") UUID customerID) throws Exception {
        return new  ResponseEntity<>(customerService.getCustomerPeselNumber(customerID), HttpStatus.OK);
    }


}
