package com.example.rentalservices.payload.car;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

import java.util.Objects;
import java.util.UUID;

public class CarDto {
    private UUID uuid;
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

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

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
        CarDto carDto = (CarDto) o;
        return Objects.equals(uuid, carDto.uuid) && Objects.equals(type, carDto.type) && Objects.equals(model, carDto.model) && Objects.equals(brand, carDto.brand) && Objects.equals(registrationNumber, carDto.registrationNumber) && Objects.equals(pricePerDay, carDto.pricePerDay) && Objects.equals(year, carDto.year) && Objects.equals(vin, carDto.vin) && Objects.equals(mileage, carDto.mileage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, type, model, brand, registrationNumber, pricePerDay, year, vin, mileage);
    }

    @Override
    public String toString() {
        return "CarDto{" +
                "uuid=" + uuid +
                ", type='" + type + '\'' +
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
