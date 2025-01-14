package com.example.rentalservices.controller;

import com.azure.core.annotation.QueryParam;
import com.example.rentalservices.payload.car.CarDto;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.payload.car.ShortCarDto;
import com.example.rentalservices.service.CarService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }




    @GetMapping("/cars")
    public ResponseEntity<List<ShortCarDto>> getAllCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @GetMapping("/cars/{carId}")
    public ResponseEntity<CarDto> getCarById(@PathVariable UUID carId){
        return new ResponseEntity<>(carService.getCarById(carId),HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/cars")
    public ResponseEntity<String> createNewCar(NewCar newCar) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(carService.addNewCar(newCar, userDetails.getUsername()), HttpStatus.CREATED);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/cars/{carId}")
    public ResponseEntity<String> updateCar(@PathVariable UUID carId,@RequestBody NewCar newCar) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(carService.updateCar(carId,newCar,userDetails.getUsername()), HttpStatus.OK);
    }
}
