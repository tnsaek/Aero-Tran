package edu.miu.cs489.aerotran.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "flight")
    private List<Passenger> passengers = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "aircraft_id")
    private Aircraft aircraft;
    @ManyToOne
    @JoinColumn(name = "departure_airport")
//    @JsonManagedReference
    private Airport departureAirport;
    @ManyToOne
    @JoinColumn(name = "arrival_airport")
//    @JsonManagedReference
    private Airport arrivalAirport;
}
