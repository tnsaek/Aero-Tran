package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import org.springframework.data.domain.Page;
import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    public Flight addFlight(Flight flight);
    public Page<Flight> getAllFlightsPaged(Integer pageNumber);
    public Flight getFlight(Long flightId);
    public List<Flight> flightsFromDepCityToArrCity(String departureCity, String arrivalCity);
    public List<Flight> FlightsWithDepDateFromDepCityToArrCity(Airport departureAirport, Airport arrivalAirport, LocalDate departureDate);
    public Flight updateFlight(Flight flight, Long flightId);
    public void cancelFlight(Long flightId);
}
