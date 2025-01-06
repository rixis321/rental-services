package com.example.rentalservices.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ActionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logID;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "employeeID", nullable = false)
    private Employee employee;

    private Date actionDate;
    private String actionType;
}
