package com.example.rentalservices.repository;

import com.example.rentalservices.model.EventLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
}
