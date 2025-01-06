package com.example.rentalservices.model;
import com.example.rentalservices.model.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentID;

   // @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "reservationID", nullable = false)
    private Reservation reservation;

    private Double amount;
    private Date paymentDate;
    private PaymentMethod paymentMethod;
    private String status;
}
