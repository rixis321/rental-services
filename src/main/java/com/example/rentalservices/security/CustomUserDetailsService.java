package com.example.rentalservices.security;

import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.Employee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found")
        );
        if (customer != null) {
            Set<GrantedAuthority> authorities = Collections.singleton(
                    new SimpleGrantedAuthority(customer.getRole().getName()));
            return new org.springframework.security.core.userdetails.User(
                    customer.getEmail(), customer.getPassword(), authorities);
        }

        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));

        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(employee.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(), employee.getPassword(), authorities);
    }
}

