package com.example.rentalservices.mapper;


import com.example.rentalservices.model.Car;
import com.example.rentalservices.payload.car.CarDto;
import com.example.rentalservices.payload.car.NewCar;
import com.example.rentalservices.payload.car.ShortCarDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper( CarMapper.class );

    ShortCarDto mapToShortCarDto(Car car);

    CarDto mapToCarDto(Car car);
    Car mapToCar(NewCar newCar);

}
