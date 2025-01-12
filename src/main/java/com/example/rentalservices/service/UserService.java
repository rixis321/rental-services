package com.example.rentalservices.service;

import java.util.UUID;

public interface UserService {

    public boolean doesUserExistByEmailAndId(String email, UUID id);
}
