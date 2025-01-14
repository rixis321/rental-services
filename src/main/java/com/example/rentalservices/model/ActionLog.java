package com.example.rentalservices.model;
import com.example.rentalservices.model.enums.ActionType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.Objects;
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
    private ActionType actionType;

    public Long getLogID() {
        return logID;
    }

    public void setLogID(Long logID) {
        this.logID = logID;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActionLog actionLog = (ActionLog) o;
        return Objects.equals(logID, actionLog.logID) && Objects.equals(uuid, actionLog.uuid) && Objects.equals(employee, actionLog.employee) && Objects.equals(actionDate, actionLog.actionDate) && actionType == actionLog.actionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(logID, uuid, employee, actionDate, actionType);
    }

    @Override
    public String toString() {
        return "ActionLog{" +
                "logID=" + logID +
                ", uuid=" + uuid +
                ", employee=" + employee +
                ", actionDate=" + actionDate +
                ", actionType=" + actionType +
                '}';
    }
}
