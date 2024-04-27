package edu.miu.cs489.aerotran.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flightId;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private double price;
    @JsonManagedReference
    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Passenger> passengers = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
    @ManyToOne()
    @JoinColumn(name = "departure_airport")
    @JsonManagedReference
    private Airport departureAirport;
    @ManyToOne()
    @JoinColumn(name = "arrival_airport")
    @JsonManagedReference
    private Airport arrivalAirport;

    public Flight(Long flightId, String flightNumber, LocalDate departureDate, LocalDate arrivalDate, LocalTime departureTime, LocalTime arrivalTime, double price) {
        this.flightId = flightId;
        this.flightNumber = flightNumber;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.price = price;
    }
}
