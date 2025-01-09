package com.example.rentalservices.service.impl;

import com.example.rentalservices.model.EventLog;
import com.example.rentalservices.model.enums.EventType;
import com.example.rentalservices.repository.EventLogRepository;
import com.example.rentalservices.service.EventLogService;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class EventLogServiceImpl implements EventLogService {
    private final EventLogRepository eventLogRepository;

    public EventLogServiceImpl(EventLogRepository eventLogRepository) {
        this.eventLogRepository = eventLogRepository;
    }

    @Override
    public void logEvent(EventType eventType, String message, String email) {
        EventLog eventLog = new EventLog();
        eventLog.setUuid(UUID.randomUUID());
        eventLog.setMessage(message);
        eventLog.setEventType(eventType);
        eventLog.setEmail(email);

        eventLogRepository.save(eventLog);
    }

    @Override
    public void logEvent(EventType eventType, String message) {
        EventLog eventLog = new EventLog();
        eventLog.setUuid(UUID.randomUUID());
        eventLog.setMessage(message);
        eventLog.setEventType(eventType);

        eventLogRepository.save(eventLog);
    }
}
