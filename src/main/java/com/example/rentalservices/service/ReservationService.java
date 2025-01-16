package com.example.rentalservices.service;

import com.example.rentalservices.payload.reservation.NewReservation;
import com.example.rentalservices.payload.reservation.ShortReservation;

import java.util.List;
import java.util.UUID;

public interface ReservationService {

    String createReservation(NewReservation newReservation, UUID customerID,UUID carId);

    List<ShortReservation> getAllReservations();

    List<ShortReservation> getCustomerReservations(UUID customerId);
}
