package com.example.rentalservices.service;

import com.example.rentalservices.payload.car.CarDto;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.payload.car.ShortCarDto;

import java.util.UUID;

public interface CarService {

    ShortCarDto getAllCars();

    CarDto getCarById(UUID carId);

    String addNewCar(NewCar newCar);
}
