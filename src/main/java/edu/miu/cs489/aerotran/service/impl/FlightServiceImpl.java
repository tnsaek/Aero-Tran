package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.entity.Aircraft;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;
import edu.miu.cs489.aerotran.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements IFlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper mapper;

    @Override
    public FlightDto addFlight(FlightDto flightDto) {
        Airport arrivalAirport = mapper.map(flightDto.getDepartureAirport(), Airport.class);
        Airport departureAirport = mapper.map(flightDto.getDepartureAirport(), Airport.class);
        Aircraft aircraft = mapper.map(flightDto.getAircraft(), Aircraft.class);
        Flight flight = mapper.map(flightDto, Flight.class);
        System.out.println(flight);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureAirport(departureAirport);
        flight.setAircraft(aircraft);
        Flight savedFlight = flightRepository.save(flight);
        return mapper.map(savedFlight, FlightDto.class);
    }

    @Override
    public Page<FlightDto> getAllFlightsPaged(Integer pageNumber) {
        Page<Flight> flights = flightRepository.findAll(PageRequest.of(pageNumber, 10, Sort.by("departureAirport")));
        Page<FlightDto> flightDtos = new PageImpl<>(flights.stream().map(flight ->
                mapper.map(flight, FlightDto.class)).collect(Collectors.toList()));
        return flightDtos;
    }

    @Override
    public FlightDto getFlight(Long flightId) {
       Flight flight = flightRepository.findById(flightId).orElse(null);
       return mapper.map(flight, FlightDto.class);
    }

    @Override
    public List<FlightDto> flightsFromDepCityToArrCity(String departureAirport, String arrivalAirport) {
        Optional<List<Flight>> flights =  flightRepository.findFlightByDepartureCityAndArrivalCity(departureAirport, arrivalAirport);
        if (flights.isPresent()) {
            return flights.stream().map(flights1 ->
                    mapper.map(flights1, FlightDto.class)).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<FlightDto> FlightsWithDepDateFromDepCityToArrCity(LocalDate departureDate, AirportDto departureAirportDto, AirportDto arrivalAirportDto) {
        Airport departureAirport = mapper.map(departureAirportDto, Airport.class);
        Airport arrivalAirport = mapper.map(arrivalAirportDto, Airport.class);
        Optional<List<Flight>> flights = flightRepository.findFlightByDepartureDateAndDepartureCityAndArrivalCity(
                departureDate, departureAirport, arrivalAirport);
        if (flights.isPresent()) {
            return flights.stream().map(flights1 ->
                    mapper.map(flights1, FlightDto.class)).collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public FlightDto updateFlight(FlightDto flightDto, Long flightId) {
        Flight flightToBeUpdated = flightRepository.findById(flightId).orElse(null);
        Flight flight = mapper.map(flightDto, Flight.class);
        flightToBeUpdated = flight;
        flightRepository.save(flightToBeUpdated);
        return mapper.map(flightToBeUpdated, FlightDto.class);
    }

    @Override
    public void cancelFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

}
