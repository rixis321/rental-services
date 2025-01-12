package com.example.rentalservices.repository;

import com.example.rentalservices.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phoneNumber);

    Optional<Customer> findByUuid(UUID uuid);
}
