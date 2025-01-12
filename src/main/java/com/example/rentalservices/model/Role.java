package com.example.rentalservices.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleID;

    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    private String name;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Employee> employees;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<Customer> customers;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(roleID, role.roleID) && Objects.equals(uuid, role.uuid) && Objects.equals(name, role.name) && Objects.equals(employees, role.employees) && Objects.equals(customers, role.customers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleID, uuid, name, employees, customers);
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleID=" + roleID +
                ", uuid=" + uuid +
                ", name='" + name + '\'' +
                ", employees=" + employees +
                ", customers=" + customers +
                '}';
    }
}
