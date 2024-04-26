package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;
import edu.miu.cs489.aerotran.service.IFlightService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements IFlightService {

    private final FlightRepository flightRepository;
    private final ModelMapper mapper;

    @Override
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);

    }

    @Override
    public Page<Flight> getAllFlightsPaged(Integer pageNumber) {
        return flightRepository.findAll(PageRequest.of(pageNumber, 10, Sort.by("departureAirport")));

    }

    @Override
    public Flight getFlight(Long flightId) {
       return flightRepository.findById(flightId).orElse(null);

    }

    @Override
    public List<Flight> flightsFromDepCityToArrCity(String departureAirport, String arrivalAirport) {
        return flightRepository.findFlightByDepartureCityAndArrivalCity(departureAirport, arrivalAirport).orElse(null);
    }

    @Override
    public List<Flight> FlightsWithDepDateFromDepCityToArrCity(Airport departureAirport, Airport arrivalAirport, LocalDate departureDate) {

        return flightRepository.findFlightByDepartureDateAndDepartureCityAndArrivalCity(
                departureDate, departureAirport, arrivalAirport).orElse(null);

    }

    @Override
    public Flight updateFlight(Flight flight, Long flightId) {
        Flight flightToBeUpdated = flightRepository.findById(flightId).orElse(null);
        flightToBeUpdated = flight;
        return flightRepository.save(flightToBeUpdated);
    }

    @Override
    public void cancelFlight(Long flightId) {
        flightRepository.deleteById(flightId);
    }

}
