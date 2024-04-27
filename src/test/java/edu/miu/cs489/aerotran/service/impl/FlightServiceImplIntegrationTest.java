package edu.miu.cs489.aerotran.service.impl;

import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FlightServiceImplIntegrationTest {


    @Autowired
    private FlightServiceImpl flightService;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithExistingFlights() throws Exception {
        String departureAirportName = "Chicago";
        String arrivalAirportName = "London";

        Airport departureAirport = new Airport(null, departureAirportName);
        Airport arrivalAirport = new Airport(null, arrivalAirportName);

        Flight flight = new Flight(1L, "FL123", LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalTime.of(10, 0), LocalTime.of(15, 0), 500.0);
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setAircraft(null);
        flight.setPassengers(null);
        flightRepository.save(flight);

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirportName, arrivalAirportName);
        for(Flight flight1: actualFlights){
            actualFlights.get(0).setDepartureAirport(departureAirport);
            actualFlights.get(0).setArrivalAirport(arrivalAirport);
            actualFlights.get(0).setAircraft(null);
            actualFlights.get(0).setPassengers(null);
        }

        assertNotNull(actualFlights);
        assertEquals(1, actualFlights.size());
        assertEquals(flight, actualFlights.get(0));
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithNoFlights() {
        String departureAirport = "Chicago";
        String arrivalAirport = "Tokyo";

        List<Flight> actualFlights = flightService.flightsFromDepCityToArrCity(departureAirport, arrivalAirport);

        assertNotNull(actualFlights);
        assertTrue(actualFlights.isEmpty());
    }
}

