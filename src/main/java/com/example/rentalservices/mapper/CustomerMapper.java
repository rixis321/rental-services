package com.example.rentalservices.mapper;


import com.example.rentalservices.model.Customer;
import com.example.rentalservices.payload.ShortCustomerDto;
import com.example.rentalservices.payload.NewCustomer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer mapToCustomer(NewCustomer newCustomer);

    ShortCustomerDto mapToCustomerDto(Customer customer);
}
