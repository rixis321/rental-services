package com.example.rentalservices.validator;

import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.repository.CarRepository;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.regex.Pattern;

@Component
public class CarDataValidator {
    private final CarRepository carRepository;

    public CarDataValidator(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public boolean validateCarData(NewCar carDto) {
        return validateStringField(carDto.getType(), "Car type") &&
                validateStringField(carDto.getModel(), "Car model") &&
                validateStringField(carDto.getBrand(), "Car brand") &&
                validateLicensePlate(carDto.getRegistrationNumber()) &&
                validateVinNumber(carDto.getVin()) &&
                validateProductionYear(carDto.getYear()) &&
                validateNumberField(carDto.getPricePerDay(), "Price per day") &&
                validateNumberField(carDto.getMileage(), "Mileage");
    }

    private boolean validateStringField(String value, String fieldName) {
        if (value == null || !value.matches("^[a-zA-Z]+$")) {
            throw new ValidationException(fieldName + " must be a valid string containing only letters");
        }
        return true;
    }

    private boolean validateNumberField(Number value, String fieldName) {
        if (value == null || !isNumeric(value)) {
            throw new ValidationException(fieldName + " must be a valid number");
        }
        return true;
    }

    private boolean validateLicensePlate(String licensePlate) {
        String pattern = "^[A-Z]{2}\\d{5}$|^[A-Z]{2}\\d{4}[A-Z]$";
        if (licensePlate == null) {
            throw new ValidationException("License plate cannot be null");
        }
        if (!Pattern.matches(pattern, licensePlate)) {
            throw new ValidationException("Invalid license plate format");
        }
        return true;
    }

    private boolean validateVinNumber(String vin) {
        String pattern = "^[A-HJ-NPR-Z0-9]{17}$";
        if (vin == null) {
            throw new ValidationException("VIN number cannot be null");
        }
        if (!Pattern.matches(pattern, vin)) {
            throw new ValidationException("Invalid VIN number");
        }
        return true;
    }

    private boolean validateProductionYear(int year) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (year < 1886 || year > currentYear) {
            throw new ValidationException("Invalid production year. Year must be between 1886 and " + currentYear);
        }
        return true;
    }

    private boolean isNumeric(Number value) {
        try {
            Double.parseDouble(value.toString());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
