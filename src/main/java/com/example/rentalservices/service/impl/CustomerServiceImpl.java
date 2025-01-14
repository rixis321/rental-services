package com.example.rentalservices.service.impl;

import com.example.rentalservices.exception.RentalServiceApiException;
import com.example.rentalservices.exception.ResourceNotFoundException;
import com.example.rentalservices.mapper.CustomerMapper;
import com.example.rentalservices.model.Customer;
import com.example.rentalservices.model.enums.EventType;
import com.example.rentalservices.payload.customer.NewCustomer;
import com.example.rentalservices.payload.customer.ShortCustomerDto;
import com.example.rentalservices.repository.CustomerRepository;
import com.example.rentalservices.service.CustomerService;
import com.example.rentalservices.service.EventLogService;
import com.example.rentalservices.validator.PeselHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PeselHandler peselHandler;
    private final EventLogService eventLogService;
    private final CustomerMapper customerMapper;
    public CustomerServiceImpl(CustomerRepository customerRepository, PeselHandler peselHandler, EventLogService eventLogService, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.peselHandler = peselHandler;
        this.eventLogService = eventLogService;
        this.customerMapper = customerMapper;
    }


    @Override
    public String getCustomerPeselNumber(UUID customerId) {

        Customer customer = customerRepository.findByUuid(customerId).orElseThrow(
                ()->new ResourceNotFoundException("CustomerID",customerId.toString())
        );

        try {

            String decryptedPesel = customer.getPesel();
            //TODO CHANGES HERE DEPEND ON FRONTEND
           // String decryptedPesel = peselHandler.decryptPesel(customer.getPesel());
           // eventLogService.logEvent(EventType.PESEL_DECRYPTION_SUCCESS,"Attempting pesel number",customer.getEmail());
            return decryptedPesel;
        }catch (Exception e){
            e.printStackTrace();
            eventLogService.logEvent(EventType.PESEL_DECRYPTION_FAILED,"Attempting pesel number",customer.getEmail());
            throw new RentalServiceApiException(HttpStatus.INTERNAL_SERVER_ERROR,"Failed to decrypt PESEL for customer");
        }

    }

    @Override
    public ShortCustomerDto getCustomer(UUID customerId) {

        Customer customer = customerRepository.findByUuid(customerId).orElseThrow(
                ()->new ResourceNotFoundException("CustomerID",customerId.toString())
        );
        return customerMapper.mapToCustomerDto(customer);
    }

    @Override
    public List<ShortCustomerDto> getAllCustomers() {

        return customerRepository.findAll().stream()
                .map(customerMapper::mapToCustomerDto)
                .toList();
    }


}
