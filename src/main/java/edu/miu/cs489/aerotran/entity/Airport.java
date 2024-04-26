package edu.miu.cs489.aerotran.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long airportId;
    private  String name;
    @OneToMany(mappedBy = "departureAirport", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Flight> departureFlights;
    @OneToMany(mappedBy = "arrivalAirport", cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<Flight> arrivalFlights;
    @OneToMany(mappedBy = "departureAirport")
    @JsonBackReference
    private List<Flight> flights;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
