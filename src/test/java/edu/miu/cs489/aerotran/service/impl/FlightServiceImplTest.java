package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class FlightServiceImplTest {

    @MockBean
    private FlightRepository flightRepository;
    @Autowired
    private FlightServiceImpl flightService;

    @Test
    public void testFlightsFromDepCityToArrCity_WithExistingFlights() {
        String departureAirport = "Chicago";
        String arrivalAirport = "London";

        List<Flight> expectedFlights = new ArrayList<>();
        expectedFlights.add(new Flight(1L, "FL123", LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalTime.of(10, 0), LocalTime.of(15, 0), 500.0));

        Optional<List<Flight>> optionalFlights = Optional.of(expectedFlights);
        when(flightRepository.findFlightByDepartureCityAndArrivalCity(departureAirport, arrivalAirport)).thenReturn(optionalFlights);

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirport, arrivalAirport);

        assertNotNull(actualFlights);
        assertEquals(expectedFlights, actualFlights);
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithNoFlights() {
        String departureAirport = "Chicago";
        String arrivalAirport = "Tokyo";

        when(flightRepository.findFlightByDepartureCityAndArrivalCity(departureAirport, arrivalAirport)).thenReturn(Optional.empty());

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirport, arrivalAirport);

        assertNotNull(actualFlights);
        assertTrue(actualFlights.isEmpty());
    }
}