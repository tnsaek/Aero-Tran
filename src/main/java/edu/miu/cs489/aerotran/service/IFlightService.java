package edu.miu.cs489.aerotran.service;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface IFlightService {
    public FlightDto addFlight(FlightDto flightDto);
    public Page<FlightDto> getAllFlightsPaged(Integer pageNumber);
    public FlightDto getFlight(Long flightId);
    public List<FlightDto> flightsFromDepCityToArrCity(String departureCity, String arrivalCity);
    public List<FlightDto> FlightsWithDepDateFromDepCityToArrCity(LocalDate departureDate, AirportDto departureCity, AirportDto arrivalCity);
    public FlightDto updateFlight(FlightDto flightDto, Long flightId);
    public void cancelFlight(Long flightId);
}
