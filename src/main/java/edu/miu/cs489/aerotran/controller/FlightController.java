package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.service.IAircraftService;
import edu.miu.cs489.aerotran.service.IAirportService;
import edu.miu.cs489.aerotran.service.IFlightService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class FlightController {

    private final IFlightService flightService;
    private final IAircraftService aircraftService;
    private final IAirportService airportService;
    private final ModelMapper mapper;


    @GetMapping("/flight/new")
    public ModelAndView showNewFlightPage() {
        ModelAndView modelAndView = new ModelAndView("secured/flight/newFlight");
        modelAndView.addObject("flight", new FlightDto());
        modelAndView.addObject("aircrafts", aircraftService.getAllAircrafts());
        modelAndView.addObject("airports", airportService.getAllAirports());
        return modelAndView;
    }

    @PostMapping("/flight/new")
    public ModelAndView saveFlight(@Valid @ModelAttribute("flight") FlightDto flightDto, BindingResult bindingResult,
                                   @RequestParam("departureAirport") Long departureAirport,
                                   @RequestParam("arrivalAirport") Long arrivalAirport, @RequestParam(required = false) Long aircraftId,
                                   @RequestParam("arrivalTime") String arrivalTime, @RequestParam("departureTime") String departureTime) {

        ModelAndView modelAndView = new ModelAndView();
        System.out.println("departureAirport " + departureAirport);
        System.out.println("arrivalAirport " + arrivalAirport);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errors", bindingResult.getAllErrors());
        }

        if (departureAirport.equals(arrivalAirport)) {
            modelAndView.addObject("sameAirportError", "Departure and Destination Airports can't be the same");
        }

        if (bindingResult.hasErrors() || departureAirport.equals(arrivalAirport)) {
            modelAndView.addObject("flight", new FlightDto());
            modelAndView.addObject("aircrafts", aircraftService.getAllAircrafts());
            modelAndView.addObject("airports", airportService.getAllAirports());

            System.out.println(aircraftService.getAllAircrafts());
            System.out.println(airportService.getAllAirports());

            modelAndView.setViewName("secured/flight/newFlight");
            return modelAndView;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:MM");
        LocalTime arrivalTime1 = LocalTime.parse(arrivalTime, formatter);
        LocalTime departureTime1 = LocalTime.parse(departureTime, formatter);

        flightDto.setAircraft(aircraftService.getAircraft(aircraftId));
        flightDto.setDepartureAirport(airportService.getAirport(departureAirport));
        flightDto.setArrivalAirport(airportService.getAirport(arrivalAirport));
        flightDto.setDepartureTime(departureTime1);
        flightDto.setArrivalTime(arrivalTime1);
        flightService.addFlight(flightDto);

        System.out.println("flightDto " + flightDto);

        modelAndView.addObject("flights", flightService.getAllFlightsPaged(0));
        modelAndView.addObject("currentPage", 0);
        modelAndView.setViewName("secured/flight/flights");

        return modelAndView;
    }

    @GetMapping("/flights")
    public ModelAndView getAllFlightsPaged(@RequestParam(defaultValue = "0") int pageNo) {

        ModelAndView modelAndView = new ModelAndView("secured/flight/flights");
        modelAndView.addObject("flights", flightService.getAllFlightsPaged(pageNo));
        modelAndView.addObject("currentPage", pageNo);
        return modelAndView;

    }

    @GetMapping("/flight/search")
    public ModelAndView searchFlightPage() {

        ModelAndView modelAndView = new ModelAndView("secured/flight/searchFlight");
        modelAndView.addObject("airports", airportService.getAllAirports());
        modelAndView.addObject("flights", null);
        return modelAndView;
    }

    @PostMapping("/flight/search")
    public ModelAndView searchFlight(@RequestParam("departureAirport") Long departureAirport,
                                     @RequestParam("arrivalAirport") Long arrivalAirport,
                                     @RequestParam("departureTime") LocalDate departureDate) {

        ModelAndView modelAndView = new ModelAndView("secured/flight/searchFlight");
        AirportDto depAirport = airportService.getAirport(departureAirport);
        AirportDto destAirport = airportService.getAirport(arrivalAirport);

        if (depAirport.equals(destAirport)) {
            modelAndView.addObject("AirportError", "Departure and Arrival have to be different");
            modelAndView.addObject("airports", airportService.getAllAirports());
            return modelAndView;
        }

        List<FlightDto> flights = flightService.FlightsWithDepDateFromDepCityToArrCity(departureDate, depAirport, destAirport);
        if (flights.isEmpty()) {
            modelAndView.addObject("notFound", "check another date");
        } else {
            modelAndView.addObject("flights", flights);
        }

        modelAndView.addObject("airports", airportService.getAllAirports());
        return modelAndView;

    }

    @GetMapping("/flight/book")
    public ModelAndView showBookFlightPage() {

        ModelAndView modelAndView = new ModelAndView("secured/flight/bookFlight");
        modelAndView.addObject("airports", airportService.getAllAirports());
        return modelAndView;

    }

    @PostMapping("/flight/book")
    public ModelAndView searchFlightToBook(@RequestParam("departureAirport") Long departureAirport,
                                           @RequestParam("destinationAirport") Long destinationAirport,
                                           @RequestParam("departureDate") LocalDate departureDate) {

        ModelAndView modelAndView = new ModelAndView("secured/flight/bookFlight");
        AirportDto departurAirport = airportService.getAirport(departureAirport);
        AirportDto arrivalAirport = airportService.getAirport(destinationAirport);

        if (departurAirport == arrivalAirport) {
            modelAndView.addObject("AirportError", "Departure airport and Arrival have to be different");
            modelAndView.addObject("airports", airportService.getAllAirports());
            return modelAndView;
        }

        List<FlightDto> flights = flightService.FlightsWithDepDateFromDepCityToArrCity(departureDate, departurAirport, arrivalAirport);
        if (flights.isEmpty()) {
            modelAndView.addObject("notFound", "Check another date");
        } else {
            modelAndView.addObject("flights", flights);
        }

        modelAndView.addObject("airports", airportService.getAllAirports());
        return modelAndView;

    }


    @GetMapping("/flight/delete")
    public ModelAndView deleteFlight(@PathParam("flightId") Long flightId) {

        flightService.cancelFlight(flightId);
        ModelAndView modelAndView = new ModelAndView("secured/flight/flights");
        modelAndView.addObject("flights", flightService.getAllFlightsPaged(0));
        modelAndView.addObject("currentPage", 0);
        return modelAndView;

    }

}
