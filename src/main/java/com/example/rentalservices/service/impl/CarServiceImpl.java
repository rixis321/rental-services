package com.example.rentalservices.service.impl;

import com.example.rentalservices.exception.ResourceNotFoundException;
import com.example.rentalservices.exception.ValidationException;
import com.example.rentalservices.mapper.CarMapper;
import com.example.rentalservices.model.ActionLog;
import com.example.rentalservices.model.Car;
import com.example.rentalservices.model.Employee;
import com.example.rentalservices.model.enums.ActionType;
import com.example.rentalservices.payload.car.CarDto;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.payload.car.ShortCarDto;
import com.example.rentalservices.repository.ActionLogRepository;
import com.example.rentalservices.repository.CarRepository;
import com.example.rentalservices.repository.EmployeeRepository;
import com.example.rentalservices.security.auth.impl.AuthServiceImpl;
import com.example.rentalservices.service.CarService;
import com.example.rentalservices.validator.CarDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class CarServiceImpl implements CarService {

    Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CarDataValidator carDataValidator;
    private final EmployeeRepository employeeRepository;
    private final ActionLogRepository actionLogRepository;

    public CarServiceImpl(CarRepository carRepository, CarMapper carMapper, CarDataValidator carDataValidator, EmployeeRepository employeeRepository, ActionLogRepository actionLogRepository) {
        this.carRepository = carRepository;
        this.carMapper = carMapper;
        this.carDataValidator = carDataValidator;
        this.employeeRepository = employeeRepository;
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    public List<ShortCarDto> getAllCars() {
        return carRepository.findAll()
                .stream()
                .map(carMapper::mapToShortCarDto).toList();
    }

    @Override
    public CarDto getCarById(UUID carId) {

        Car car = carRepository.findByUuid(carId).orElseThrow(()-> new ResourceNotFoundException("Car",carId.toString()));
        logger.error("Car not found");
        return carMapper.mapToCarDto(car);
    }

    @Override
    public String addNewCar(NewCar newCar,String employeeEmail) throws ParseException {
        try{
            carDataValidator.validateCarData(newCar);
            if(carRepository.existsByRegistrationNumber(newCar.getRegistrationNumber())) {
                throw new ValidationException("Car with that registration number already exists");
            } else if (carRepository.existsByVin(newCar.getVin())) {
                throw new ValidationException("Car with that VIN number already exists");
            }
           Employee employee =  employeeRepository.findByEmail(employeeEmail)
                    .orElseThrow(()-> new ResourceNotFoundException("Employee",employeeEmail));

            ActionLog actionLog = new ActionLog();
            actionLog.setUuid(UUID.randomUUID());
            actionLog.setActionType(ActionType.ADD_CAR);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(new Date());
            actionLog.setActionDate(formatter.parse(formattedDate));
            actionLog.setEmployee(employee);
            employee.getActionLogs().add(actionLog);
            employeeRepository.save(employee);
            logger.info("Action log added to employee");


            Car car = carMapper.mapToCar(newCar);
            car.setUuid(UUID.randomUUID());
            car.setAvailable(true);
            car = carRepository.save(car);
            logger.info("New car created");
            return "New car created";
        }
        catch (ValidationException | ParseException e){
            logger.error(e.getMessage());
            throw e;
        }
    }
    @Override
    public String updateCar(UUID carId, NewCar newCar,String employeeEmail) throws ParseException {
        try{
            Car car = carRepository.findByUuid(carId).orElseThrow(()-> new ResourceNotFoundException("Car",carId.toString()));
            carDataValidator.validateCarData(newCar);
            if(!newCar.getRegistrationNumber().equals(car.getRegistrationNumber())){
                if(carRepository.existsByRegistrationNumber(newCar.getRegistrationNumber())){
                    throw new ValidationException("Car with that registration number already exists");
                }
            }
            if(!newCar.getVin().equals(car.getVin())){
                if(carRepository.existsByVin(newCar.getVin())){
                    throw new ValidationException("Car with that VIN number already exists");
                }
            }
            Employee employee =  employeeRepository.findByEmail(employeeEmail)
                    .orElseThrow(()-> new ResourceNotFoundException("Employee",employeeEmail));

            ActionLog actionLog = new ActionLog();
            actionLog.setUuid(UUID.randomUUID());
            actionLog.setActionType(ActionType.UPDATE_CAR);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(new Date());
            actionLog.setActionDate(formatter.parse(formattedDate));
            actionLog.setEmployee(employee);
            employee.getActionLogs().add(actionLog);
            employeeRepository.save(employee);
            logger.info("Action log added to employee");

            Car updatedCar = carMapper.mapToCar(newCar);
            updatedCar.setCarID(car.getCarID());
            updatedCar.setUuid(car.getUuid());
            updatedCar.setAvailable(car.isAvailable());
            carRepository.save(updatedCar);
            logger.info("Car updated");
            return "Car updated";
        }
        catch (ValidationException | ParseException e){
                logger.error(e.getMessage());
                 throw e;
        }



    }
}
