package com.example.rentalservices.service;

import com.example.rentalservices.model.enums.EventType;
import com.example.rentalservices.payload.EventLogDto;

import java.util.List;

public interface EventLogService {

    void logEvent(EventType eventType, String message, String email);

    void logEvent(EventType eventType, String message);

    List<EventLogDto> getAllEventLogs();
}
