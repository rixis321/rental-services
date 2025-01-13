package com.example.rentalservices.security.auth;

import com.example.rentalservices.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface AccessControlService {

     boolean isAdmin(UserDetails userDetails);
     boolean isClientWithAccess(UserDetails userDetails, UUID userId);
}
