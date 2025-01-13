package com.example.rentalservices.payload.car;

import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;

public class NewCar {
    private String type;
    private String model;
    private String brand;
    @NotEmpty(message = "Registration number should not be null or empty")
    private String registrationNumber;
    private Double pricePerDay;
    private Integer year;
    @NotEmpty(message = "VIN should not be null or empty")
    private String vin;
    private Integer mileage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "Registration number should not be null or empty") String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(@NotEmpty(message = "Registration number should not be null or empty") String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public @NotEmpty(message = "VIN should not be null or empty") String getVin() {
        return vin;
    }

    public void setVin(@NotEmpty(message = "VIN should not be null or empty") String vin) {
        this.vin = vin;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewCar newCar = (NewCar) o;
        return Objects.equals(type, newCar.type) && Objects.equals(model, newCar.model) && Objects.equals(brand, newCar.brand) && Objects.equals(registrationNumber, newCar.registrationNumber) && Objects.equals(pricePerDay, newCar.pricePerDay) && Objects.equals(year, newCar.year) && Objects.equals(vin, newCar.vin) && Objects.equals(mileage, newCar.mileage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, model, brand, registrationNumber, pricePerDay, year, vin, mileage);
    }

    @Override
    public String toString() {
        return "NewCar{" +
                "type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", year=" + year +
                ", vin='" + vin + '\'' +
                ", mileage=" + mileage +
                '}';
    }
}
