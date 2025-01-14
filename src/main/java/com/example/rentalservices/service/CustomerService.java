package com.example.rentalservices.service;

import com.example.rentalservices.payload.customer.NewCustomer;
import com.example.rentalservices.payload.customer.ShortCustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    String getCustomerPeselNumber(UUID customerId) throws Exception;

    ShortCustomerDto getCustomer(UUID customerId);

    List<ShortCustomerDto> getAllCustomers();
}
