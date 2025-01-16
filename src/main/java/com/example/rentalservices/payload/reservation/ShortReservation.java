package com.example.rentalservices.payload.reservation;

import java.util.Date;
import java.util.Objects;

public class ShortReservation {

    private Date startDate;
    private Date endDate;
    private double price;
    private String customerEmail;
    private String customerPhone;
    private String carModel;
    private String reservationStatus;
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortReservation that = (ShortReservation) o;
        return Double.compare(price, that.price) == 0 && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate) && Objects.equals(customerEmail, that.customerEmail) && Objects.equals(customerPhone, that.customerPhone) && Objects.equals(carModel, that.carModel) && Objects.equals(reservationStatus, that.reservationStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, price, customerEmail, customerPhone, carModel, reservationStatus);
    }

    @Override
    public String toString() {
        return "ShortReservation{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", customerEmail='" + customerEmail + '\'' +
                ", customerPhone='" + customerPhone + '\'' +
                ", carModel='" + carModel + '\'' +
                ", reservationStatus='" + reservationStatus + '\'' +
                '}';
    }
}
