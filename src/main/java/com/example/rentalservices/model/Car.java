package com.example.rentalservices.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carID;

   // @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    private String type;
    private String model;
    private String brand;

    @Column(unique = true, nullable = false)
    private String registrationNumber;

    private Double pricePerDay;
    private Integer year;
    private String vin;
    private Integer mileage;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Reservation> reservations;
}
