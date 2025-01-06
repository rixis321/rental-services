package com.example.rentalservices.security;

import com.example.rentalservices.model.Employee;
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
public class EmployeeUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public EmployeeUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Employee not found with email: " + email));

        Set<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority(employee.getRole().getName()));

        return new org.springframework.security.core.userdetails.User(
                employee.getEmail(), employee.getPassword(), authorities);
    }
}
