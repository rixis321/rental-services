package com.example.rentalservices.service;

import com.example.rentalservices.payload.car.CarDto;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.payload.car.ShortCarDto;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

public interface CarService {

    List<ShortCarDto> getAllCars();

    CarDto getCarById(UUID carId);

    String addNewCar(NewCar newCar,String employeeEmail) throws ParseException;

    String updateCar(UUID carId, NewCar newCar,String employeeEmail) throws ParseException;
}
