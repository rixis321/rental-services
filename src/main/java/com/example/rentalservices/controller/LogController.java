package com.example.rentalservices.controller;

import com.example.rentalservices.payload.EventLogDto;
import com.example.rentalservices.service.EventLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LogController {
    private final EventLogService eventLogService;

    public LogController(EventLogService eventLogService) {
        this.eventLogService = eventLogService;
    }

    @GetMapping("/logs")
    public ResponseEntity<List<EventLogDto>> getAllEventLogs() {
        return new ResponseEntity<>(eventLogService.getAllEventLogs(), HttpStatus.OK);
    }
}
