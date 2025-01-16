package com.example.rentalservices.utils;

import com.example.rentalservices.model.Employee;
import com.example.rentalservices.model.Role;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AdminInitialization implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitialization(EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (!employeeRepository.existsByEmail("admin1@wp.pl")) {
            Role adminRole = roleRepository.findByName("ADMIN");

            Employee admin = new Employee();
            admin.setUuid(UUID.randomUUID());
            admin.setEmail("admin1@wp.pl");
            admin.setPassword(passwordEncoder.encode("Password123"));
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setPhone("123456789");
            admin.setRole(adminRole);

            employeeRepository.save(admin);
        }
    }
}
