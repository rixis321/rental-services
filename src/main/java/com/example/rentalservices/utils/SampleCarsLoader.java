package com.example.rentalservices.utils;

import com.example.rentalservices.model.Car;
import com.example.rentalservices.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SampleCarsLoader implements CommandLineRunner {

    private final CarRepository carRepository;

    public SampleCarsLoader(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... args) {
        if (carRepository.count() == 0) {
            String[] types = {"Sedan", "SUV", "Hatchback", "Coupe", "Convertible", "Pickup", "Van"};
            String[] brands = {"Toyota", "Ford", "BMW", "Mercedes", "Honda", "Audi", "Tesla", "Hyundai", "Kia", "Chevrolet"};
            String[] models = {"Corolla", "F-150", "X5", "C-Class", "Civic", "A4", "Model 3", "Elantra", "Sportage", "Camaro"};
            String[] licensePlates = {"AB12345", "CD67890", "EF23456", "GH78901", "IJ34567", "KL89012", "MN45678", "OP90123", "QR56789", "ST01234"};
            String[] vins = {"1HGCM82633A123456", "2T1BURHE1JC123456", "JHMFA16506S123456", "1GNEK13Z83R123456", "5YJSA1CN5CF123456",
                    "3CZRU5H74LM123456", "WBA8E9G50GNU123456", "KMHCT4AE6GU123456", "KNAGM4AD3F12345678", "3VW2K7AJ4EM123456"};

            for (int i = 0; i < 10; i++) {
                Car car = new Car();
                car.setUuid(UUID.randomUUID());
                car.setType(types[i % types.length]);
                car.setBrand(brands[i % brands.length]);
                car.setModel(models[i % models.length]);
                car.setRegistrationNumber(licensePlates[i]);
                car.setVin(vins[i]);
                car.setPricePerDay(80.0 + i * 15);
                car.setYear(2015 + (i % 10));
                car.setMileage(5000 + i * 1500);

                carRepository.save(car);
            }
        }
    }
}
