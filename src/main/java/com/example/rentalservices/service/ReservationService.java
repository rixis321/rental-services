package com.example.rentalservices.service;

import com.example.rentalservices.payload.reservation.NewReservation;
import com.example.rentalservices.payload.reservation.ReservationWithCar;
import com.example.rentalservices.payload.reservation.ShortReservation;

import java.util.List;

public interface ReservationService {

    String createReservation(NewReservation newReservation);

    List<ShortReservation> getAllReservations();

    ReservationWithCar getCustomerReservations(String customerId);
}
