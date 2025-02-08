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
    @Column(unique = true, nullable = false)
    private String vin;
    private Integer mileage;
    private boolean isAvailable;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return isAvailable == car.isAvailable && Objects.equals(carID, car.carID) && Objects.equals(uuid, car.uuid) && Objects.equals(type, car.type) && Objects.equals(model, car.model) && Objects.equals(brand, car.brand) && Objects.equals(registrationNumber, car.registrationNumber) && Objects.equals(pricePerDay, car.pricePerDay) && Objects.equals(year, car.year) && Objects.equals(vin, car.vin) && Objects.equals(mileage, car.mileage) && Objects.equals(reservations, car.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carID, uuid, type, model, brand, registrationNumber, pricePerDay, year, vin, mileage, isAvailable, reservations);
    }

    @Override
    public String toString() {
        return "Car{" +
                "carID=" + carID +
                ", uuid=" + uuid +
                ", type='" + type + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", year=" + year +
                ", vin='" + vin + '\'' +
                ", mileage=" + mileage +
                ", isAvailable=" + isAvailable +
                ", reservations=" + reservations +
                '}';
    }
}
