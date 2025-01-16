package com.example.rentalservices.repository;

import com.example.rentalservices.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN false ELSE true END " +
            "FROM Reservation r WHERE r.car.uuid = :carUUID " +
            "AND r.startDate < :endDate AND r.endDate > :startDate")
    boolean isCarAvailable(@Param("carUUID") UUID carUUID, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
