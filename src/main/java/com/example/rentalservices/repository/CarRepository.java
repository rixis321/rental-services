package com.example.rentalservices.repository;

import com.example.rentalservices.model.Car;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByUuid(UUID carId);


    boolean existsByRegistrationNumber(String registrationNumber);

    boolean existsByVin(@NotEmpty(message = "VIN should not be null or empty") String vin);
}
