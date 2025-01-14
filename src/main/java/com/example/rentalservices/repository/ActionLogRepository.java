package com.example.rentalservices.repository;

import com.example.rentalservices.model.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionLogRepository extends JpaRepository<ActionLog, Long> {
}
