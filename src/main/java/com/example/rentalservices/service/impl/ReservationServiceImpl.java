package com.example.rentalservices.service.impl;

import com.example.rentalservices.exception.ResourceNotFoundException;
import com.example.rentalservices.mapper.ReservationMapper;
import com.example.rentalservices.model.Car;
import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.Reservation;
import com.example.rentalservices.model.enums.ReservationStatus;
import com.example.rentalservices.payload.reservation.NewReservation;
import com.example.rentalservices.payload.reservation.ShortReservation;
import com.example.rentalservices.repository.CarRepository;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.repository.ReservationRepository;
import com.example.rentalservices.service.ReservationService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService {


    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    public ReservationServiceImpl(CarRepository carRepository, CustomerRepository customerRepository, ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public String createReservation(NewReservation newReservation, UUID customerID, UUID carId) {

        Car car = carRepository.findByUuid(carId)
                .orElseThrow(()->new ResourceNotFoundException("car id",carId.toString()));
        Customer customer = customerRepository.findByUuid(customerID)
                .orElseThrow(()->new ResourceNotFoundException("customer",customerID.toString()));

        Reservation reservation = new Reservation();
        reservation.setStartDate(newReservation.getStartDate());
        reservation.setEndDate(newReservation.getEndDate());
        reservation.setAmount(calculateReservationAmount(car,reservation.getStartDate(),reservation.getEndDate()));
        reservation.setCar(car);
        reservation.setCustomer(customer);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setUuid(UUID.randomUUID());


        if (!car.isAvailable()) {
            throw new IllegalArgumentException("The car is not available for the selected dates.");
        }

        reservationRepository.save(reservation);

        car.getReservations().add(reservation);
        car.setAvailable(false);
        carRepository.save(car);

        return "Reservation created";
    }

    @Override
    public List<ShortReservation> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toShortReservation)
                .toList();
    }

    @Override
    public List<ShortReservation> getCustomerReservations(UUID customerId) {
        Customer customer = customerRepository.findAllByUuid(customerId)
                .orElseThrow(()->new ResourceNotFoundException("customer",customerId.toString()));


        return customer.getReservations()
                .stream()
                .map(reservationMapper::toShortReservation)
                .toList();
    }


    private Double calculateReservationAmount(Car car, Date startDate, Date endDate) {
        long diffInMillis = endDate.getTime() - startDate.getTime();
        long days = Math.max(1, diffInMillis / (1000 * 60 * 60 * 24));
        return car.getPricePerDay() * days;
    }
}
