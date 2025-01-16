package com.example.rentalservices.mapper;

import com.example.rentalservices.model.Reservation;
import com.example.rentalservices.payload.reservation.ShortReservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {
    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);


    @Mappings({
            @Mapping(source = "startDate", target = "startDate"),
            @Mapping(source = "endDate", target = "endDate"),
            @Mapping(source = "amount", target = "price"),
            @Mapping(source = "customer.email", target = "customerEmail"),
            @Mapping(source = "customer.phone", target = "customerPhone"),
            @Mapping(source = "car.model", target = "carModel"),
            @Mapping(source = "status", target = "reservationStatus")
    })
    ShortReservation toShortReservation(Reservation reservation);

    default String mapStatusToString(Enum<?> status) {
        return status != null ? status.name() : null;
    }
}
