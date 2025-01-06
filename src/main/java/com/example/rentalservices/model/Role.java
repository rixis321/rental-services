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
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;

   // @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Employee> employees;

}
