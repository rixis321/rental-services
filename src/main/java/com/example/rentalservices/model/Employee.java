package com.example.rentalservices.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeID;

   // @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(unique = true, nullable = false, updatable = false)
    private UUID uuid;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true, nullable = false)
    private String phone;
    @ManyToOne
    @JoinColumn(name = "roleID", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<ActionLog> actionLogs;

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long employeeID) {
        this.employeeID = employeeID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<ActionLog> getActionLogs() {
        return actionLogs;
    }

    public void setActionLogs(List<ActionLog> actionLogs) {
        this.actionLogs = actionLogs;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeID, employee.employeeID) && Objects.equals(uuid, employee.uuid) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(phone, employee.phone) && Objects.equals(role, employee.role) && Objects.equals(actionLogs, employee.actionLogs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID, uuid, email, password, firstName, lastName, phone, role, actionLogs);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID=" + employeeID +
                ", uuid=" + uuid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                ", actionLogs=" + actionLogs +
                '}';
    }
}
