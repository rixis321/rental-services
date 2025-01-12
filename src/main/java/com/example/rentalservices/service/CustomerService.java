package com.example.rentalservices.service;

import com.example.rentalservices.payload.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    String getCustomerPeselNumber(UUID customerId) throws Exception;

    CustomerDto getCustomer(UUID customerId);

    List<CustomerDto> getAllCustomers();
}
