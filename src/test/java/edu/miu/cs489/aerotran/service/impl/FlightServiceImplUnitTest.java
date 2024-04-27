package edu.miu.cs489.aerotran.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.miu.cs489.aerotran.entity.Address;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.repository.FlightRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {FlightServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FlightServiceImplUnitTest {
    @MockBean
    private FlightRepository flightRepository;

    @Autowired
    private FlightServiceImpl flightServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void testGetAllFlightsPaged() {
        PageImpl<Flight> pageImpl = new PageImpl<>(new ArrayList<>());
        when(flightRepository.findAll(Mockito.<Pageable>any())).thenReturn(pageImpl);
        Page<Flight> actualAllFlightsPaged = flightServiceImpl.getAllFlightsPaged(10);
        verify(flightRepository).findAll(Mockito.<Pageable>any());
        assertTrue(actualAllFlightsPaged.toList().isEmpty());
        assertSame(pageImpl, actualAllFlightsPaged);
    }

    @Test
    public void testFlightsFromDepCityToArrCity_WithExistingFlights() {
        String departureAirport = "Chicago";
        String arrivalAirport = "London";

        List<Flight> expectedFlights = new ArrayList<>();
        expectedFlights.add(new Flight(1L, "FL123", LocalDate.now().plusDays(2), LocalDate.now().plusDays(3), LocalTime.of(10, 0), LocalTime.of(15, 0), 500.0));

        Optional<List<Flight>> optionalFlights = Optional.of(expectedFlights);
        when(flightRepository.findFlightByDepartureCityAndArrivalCity(departureAirport, arrivalAirport)).thenReturn(optionalFlights);

        List<Flight> actualFlights = flightServiceImpl.flightsFromDepCityToArrCity(departureAirport, arrivalAirport);

        assertNotNull(actualFlights);
        assertEquals(expectedFlights, actualFlights);
    }



    @Test
    void testFlightsFromDepCityToArrCity() {
        ArrayList<Flight> flightList = new ArrayList<>();
        Optional<List<Flight>> ofResult = Optional.of(flightList);
        when(flightRepository.findFlightByDepartureCityAndArrivalCity(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(ofResult);
        List<Flight> actualFlightsFromDepCityToArrCityResult = flightServiceImpl
                .flightsFromDepCityToArrCity("Departure Airport", "Arrival Airport");
        verify(flightRepository).findFlightByDepartureCityAndArrivalCity(Mockito.<String>any(), Mockito.<String>any());
        assertTrue(actualFlightsFromDepCityToArrCityResult.isEmpty());
        assertSame(flightList, actualFlightsFromDepCityToArrCityResult);
    }


    @Test
    void testCancelFlight() {
        doNothing().when(flightRepository).deleteById(Mockito.<Long>any());
        flightServiceImpl.cancelFlight(1L);
        verify(flightRepository).deleteById(Mockito.<Long>any());
    }
}
