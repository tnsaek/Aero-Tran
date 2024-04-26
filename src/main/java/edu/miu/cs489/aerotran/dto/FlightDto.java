package edu.miu.cs489.aerotran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightDto {
    private Long flightId;
    private String flightNumber;
    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private double price;
    private List<PassengerDto> passengers;
    private AircraftDto aircraft;
    private AirportDto departureAirport;
    private AirportDto arrivalAirport;
}
