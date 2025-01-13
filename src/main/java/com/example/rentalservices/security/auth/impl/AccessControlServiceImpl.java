package com.example.rentalservices.security.auth.impl;

import com.example.rentalservices.security.auth.AccessControlService;
import com.example.rentalservices.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessControlServiceImpl implements AccessControlService {

    private final UserService userService;

    public AccessControlServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean isAdmin(UserDetails userDetails) {
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }
    @Override
    public boolean isClientWithAccess(UserDetails userDetails, UUID customerId) {
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("CLIENT")) &&
                userService.doesUserExistByEmailAndId(userDetails.getUsername(), customerId);
    }
}
