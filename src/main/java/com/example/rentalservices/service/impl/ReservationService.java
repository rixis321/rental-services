package com.example.rentalservices.service.impl;

import com.example.rentalservices.payload.reservation.NewReservation;
import com.example.rentalservices.payload.reservation.ReservationWithCar;
import com.example.rentalservices.payload.reservation.ShortReservation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService implements com.example.rentalservices.service.ReservationService {
    @Override
    public String createReservation(NewReservation newReservation) {
        return "";
    }

    @Override
    public List<ShortReservation> getAllReservations() {
        return List.of();
    }

    @Override
    public ReservationWithCar getCustomerReservations(String customerId) {
        return null;
    }
}
