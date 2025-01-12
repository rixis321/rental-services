package com.example.rentalservices.service.impl;

import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.Employee;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository
            ;

    public UserServiceImpl(CustomerRepository customerRepository, EmployeeRepository employeeRepository) {
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
    }


    public boolean doesUserExistByEmailAndId(String email, UUID userId) {
        Optional<Customer> customer = customerRepository.findByEmailAndUuid(email, userId);
        if (customer.isPresent()) {
            return true;
        }
        Optional<Employee> employee = employeeRepository.findByEmailAndUuid(email, userId);
        return employee.isPresent();
    }
}
