package com.example.rentalservices.payload.car;

import java.util.Objects;
import java.util.UUID;

public class ShortCarDto {
    private UUID uuid;

    private String type;
    private String model;
    private String brand;
    private Double pricePerDay;

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

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShortCarDto that = (ShortCarDto) o;
        return Objects.equals(uuid, that.uuid) && Objects.equals(type, that.type) && Objects.equals(model, that.model) && Objects.equals(brand, that.brand) && Objects.equals(pricePerDay, that.pricePerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, type, model, brand, pricePerDay);
    }

    @Override
    public String toString() {
        return "ShortCarDto{" +
                "uuid=" + uuid +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
