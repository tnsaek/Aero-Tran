package edu.miu.cs489.aerotran.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.entity.Address;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.service.IAircraftService;
import edu.miu.cs489.aerotran.service.IAirportService;
import edu.miu.cs489.aerotran.service.IFlightService;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;

@ContextConfiguration(classes = {FlightController.class})
@ExtendWith(SpringExtension.class)
class FlightControllerUnitTest {
    @Autowired
    private FlightController flightController;

    @MockBean
    private IAircraftService iAircraftService;

    @MockBean
    private IAirportService iAirportService;

    @MockBean
    private IFlightService iFlightService;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    void testDeleteFlight() throws Exception {
        when(iFlightService.getAllFlightsPaged(Mockito.<Integer>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        doNothing().when(iFlightService).cancelFlight(Mockito.<Long>any());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/flight/delete");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("flightId", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage", "flights"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/flights"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/flights"));
    }

    @Test
    void testGetAllFlightsPaged() throws Exception {
        when(iFlightService.getAllFlightsPaged(Mockito.<Integer>any())).thenReturn(new PageImpl<>(new ArrayList<>()));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/flights");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("pageNo", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage", "flights"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/flights"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/flights"));
    }

    @Test
    void testSaveFlight() throws Exception {
        when(iAircraftService.getAllAircrafts()).thenReturn(new ArrayList<>());
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/flight/new");
        MockHttpServletRequestBuilder paramResult = postResult.param("arrivalAirport", String.valueOf(1L))
                .param("arrivalTime", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("departureAirport", String.valueOf(1L))
                .param("departureTime", "foo");
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("aircrafts", "airports", "errors", "flight", "sameAirportError"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/newFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/newFlight"));
    }

    @Test
    void testSaveFlight2() throws Exception {
        when(iAircraftService.getAllAircrafts()).thenReturn(new ArrayList<>());
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/flight/new");
        MockHttpServletRequestBuilder paramResult = postResult.param("arrivalAirport", String.valueOf(0L))
                .param("arrivalTime", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("departureAirport", String.valueOf(1L))
                .param("departureTime", "foo");
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("aircrafts", "airports", "errors", "flight"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/newFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/newFlight"));
    }

    @Test
    void testSearchFlight() throws Exception {
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());
        when(iAirportService.getAirport(Mockito.<Long>any())).thenReturn(new AirportDto());

        Address address = new Address();
        address.setAddressId(1L);
        address.setCity("Oxford");
        address.setCountry("GB");
        address.setState("MD");
        address.setStreet("Street");

        Airport airport = new Airport();
        airport.setAddress(address);
        airport.setAirportId(1L);
        airport.setArrivalFlights(new ArrayList<>());
        airport.setDepartureFlights(new ArrayList<>());
        airport.setFlights(new ArrayList<>());
        airport.setName("Name");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Airport>>any())).thenReturn(airport);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/flight/search");
        MockHttpServletRequestBuilder paramResult = postResult.param("arrivalAirport", String.valueOf(1L));
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("departureAirport", String.valueOf(1L));
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("departureTime",
                String.valueOf(LocalDate.of(1970, 1, 1)));
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("AirportError", "airports"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/searchFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/searchFlight"));
    }

    @Test
    void testSearchFlightPage() throws Exception {
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flight/search");
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("airports"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/searchFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/searchFlight"));
    }

    @Test
    void testSearchFlightToBook() throws Exception {
        when(iAirportService.getAirport(Mockito.<Long>any())).thenReturn(new AirportDto());
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());

        Address address = new Address();
        address.setAddressId(1L);
        address.setCity("Oxford");
        address.setCountry("GB");
        address.setState("MD");
        address.setStreet("Street");

        Airport airport = new Airport();
        airport.setAddress(address);
        airport.setAirportId(1L);
        airport.setArrivalFlights(new ArrayList<>());
        airport.setDepartureFlights(new ArrayList<>());
        airport.setFlights(new ArrayList<>());
        airport.setName("Name");
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<Airport>>any())).thenReturn(airport);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/flight/book");
        MockHttpServletRequestBuilder paramResult = postResult.param("departureAirport", String.valueOf(1L));
        MockHttpServletRequestBuilder paramResult2 = paramResult.param("departureDate",
                String.valueOf(LocalDate.of(1970, 1, 1)));
        MockHttpServletRequestBuilder requestBuilder = paramResult2.param("destinationAirport", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("AirportError", "airports"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/bookFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/bookFlight"));
    }

    @Test
    void testShowBookFlightPage() throws Exception {
        when(iAirportService.getAllAirports()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/flight/book");
        MockMvcBuilders.standaloneSetup(flightController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("airports"))
                .andExpect(MockMvcResultMatchers.view().name("secured/flight/bookFlight"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("secured/flight/bookFlight"));
    }

}
