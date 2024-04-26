package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@RequiredArgsConstructor
class FlightServiceImplIntegrationTest {

    private final FlightServiceImpl flightService;
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithExistingFlights() throws Exception {
        String departureAirportName = "Chicago";
        String arrivalAirportName = "London";

        AirportDto departureAirportDto = new AirportDto(null, departureAirportName);
        AirportDto arrivalAirportDto = new AirportDto(null, arrivalAirportName);

        Airport departureAirport = modelMapper.map(departureAirportDto, Airport.class);
        Airport arrivalAirport = modelMapper.map(arrivalAirportDto, Airport.class);

        Flight flight = new Flight(1L, "FL123", LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalTime.of(10, 0), LocalTime.of(15, 0), 500.0);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flightRepository.save(flight);

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirportName, arrivalAirportName);

        assertNotNull(actualFlights);
        assertEquals(1, actualFlights.size());
        assertEquals(flight, actualFlights.get(0));
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithNoFlights() {
        String departureAirport = "Chicago";
        String arrivalAirport = "Tokyo";

        // No pre-populated data, repository should be empty

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirport, arrivalAirport);

        assertNotNull(actualFlights);
        assertTrue(actualFlights.isEmpty());
    }
}