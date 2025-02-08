package com.example.rentalservices.controller;

import com.example.rentalservices.payload.reservation.NewReservation;
import com.example.rentalservices.payload.reservation.ShortReservation;
import com.example.rentalservices.security.auth.AccessControlService;
import com.example.rentalservices.service.ReservationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;
    private final AccessControlService accessControlService;
    public ReservationController(ReservationService reservationService, AccessControlService accessControlService) {
        this.reservationService = reservationService;
        this.accessControlService = accessControlService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/customers/{customerID}/cars/{carId}/reservations")
    public ResponseEntity<String> createReservation(@RequestBody NewReservation newReservation, @PathVariable UUID carId, @PathVariable UUID customerID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        if (accessControlService.isClientWithAccess(userDetails, customerID)) {
            return new ResponseEntity<>(reservationService.createReservation(newReservation,customerID,carId), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/reservations")
    public ResponseEntity<List<ShortReservation>> getAllReservations(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (accessControlService.isAdmin(userDetails)) {
            return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/customers/{customerID}/reservations")
    public ResponseEntity<List<ShortReservation>> getCustomerReservations(@PathVariable UUID customerID){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (accessControlService.isClientWithAccess(userDetails, customerID)) {
            return new ResponseEntity<>(reservationService.getCustomerReservations(customerID), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}
