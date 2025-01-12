package com.example.rentalservices.repository;

import com.example.rentalservices.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phoneNumber);

    Optional<Employee> findByUuid(UUID uuid);

    Optional<Employee> findByEmailAndUuid(String email, UUID userId);
}
