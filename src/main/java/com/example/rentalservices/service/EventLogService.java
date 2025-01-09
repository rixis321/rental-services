package com.example.rentalservices.service;

import com.example.rentalservices.model.enums.EventType;

public interface EventLogService {

    void logEvent(EventType eventType, String message, String email);

    void logEvent(EventType eventType, String message);
}
