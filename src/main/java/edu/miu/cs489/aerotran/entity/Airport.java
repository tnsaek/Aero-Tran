package edu.miu.cs489.aerotran.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Objects;

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

    public Airport(Long airportId, String name) {
        this.airportId = airportId;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(airportId, airport.airportId) && Objects.equals(name, airport.name) && Objects.equals(address, airport.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airportId, name, address);
    }
}
