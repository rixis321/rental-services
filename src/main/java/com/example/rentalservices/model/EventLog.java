package com.example.rentalservices.model;

import com.example.rentalservices.model.enums.EventType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "event_log")
public class EventLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;
    private LocalDateTime timestamp = LocalDateTime.now();

    private String email;
    @Column(nullable = false)
    private EventType eventType;


    private String message;

}
