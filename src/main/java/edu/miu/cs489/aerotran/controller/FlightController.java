package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.service.IAircraftService;
import edu.miu.cs489.aerotran.service.IAirportService;
import edu.miu.cs489.aerotran.service.IFlightService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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


    @GetMapping("/flight/new")
    public String showNewFlightPage(Model model) {

        model.addAttribute("flight", new FlightDto());
        model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
        model.addAttribute("airports", airportService.getAllAirports());
        return "secured/flight/newFlight";
    }

    @PostMapping("/flight/new")
    public String saveFlight(@Valid @ModelAttribute("flight") FlightDto flightDto, BindingResult bindingResult,
                             @RequestParam("departureAirport") Long departureAirport,
                             @RequestParam("arrivalAirport") Long arrivalAirport, @RequestParam(required = false) Long aircraftId,
                             @RequestParam("arrivalTime") String arrivalTime, @RequestParam("departureTime") String departureTime,
                             Model model) {

        System.out.println(departureAirport);

        System.out.println(departureAirport);

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
        }

        if (departureAirport == arrivalAirport) {
            model.addAttribute("sameAirportError", "Departure and Destination Airports can't be the same");
        }

        if (bindingResult.hasErrors() || departureAirport == arrivalAirport) {
            model.addAttribute("flight", new FlightDto());
            model.addAttribute("aircrafts", aircraftService.getAllAircrafts());
            model.addAttribute("airports", airportService.getAllAirports());
            return "secured/flight/newFlight";
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
        System.out.println(flightDto);

        model.addAttribute("flights", flightService.getAllFlightsPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/flight/flights";

    }

    @GetMapping("/flights")
    public String getAllFlightsPaged(@RequestParam(defaultValue = "0") int pageNo, Model model) {

        model.addAttribute("flights", flightService.getAllFlightsPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "secured/flight/flights";

    }

    @GetMapping("/flight/search")
    public String searchFlightPage(Model model) {

        model.addAttribute("airports", airportService.getAllAirports());
        model.addAttribute("flights", null);
        return "secured/flight/searchFlight";
    }

    @PostMapping("/flight/search")
    public String searchFlight(@RequestParam("departureAirport") Long departureAirport,
                               @RequestParam("arrivalAirport") Long arrivalAirport,
                               @RequestParam("departureTime") LocalDate departureDate, Model model) {

        AirportDto depAirport = airportService.getAirport(departureAirport);
        AirportDto destAirport = airportService.getAirport(arrivalAirport);

        if (depAirport == destAirport) {
            model.addAttribute("AirportError", "Departure and Arrival airport can't be same");
            model.addAttribute("aiports", airportService.getAllAirports());
            return "secured/flight/searchFlight";
        }

        List<FlightDto> flights = flightService.FlightsWithDepDateFromDepCityToArrCity(departureDate, depAirport,
                destAirport);
        if (flights.isEmpty()) {
            model.addAttribute("notFound", "No record found");
        } else {
            model.addAttribute("flights", flights);
        }

        model.addAttribute("airports", airportService.getAllAirports());
        return "secured/flight/searchFlight";

    }

    @GetMapping("/flight/book")
    public String showBookFlightPage(Model model) {

        model.addAttribute("airports", airportService.getAllAirports());
        return "secured/flight/bookFlight";
    }

    @PostMapping("/flight/book")
    public String searchFlightToBook(@RequestParam("departureAirport") Long departureAirport,
                                     @RequestParam("destinationAirport") Long destinationAirport,
                                     @RequestParam("departureDate") LocalDate departureDate, Model model) {

        AirportDto departurAirport = airportService.getAirport(departureAirport);
        AirportDto arrivalAirport = airportService.getAirport(destinationAirport);

        if (departurAirport == arrivalAirport) {

            model.addAttribute("AirportError", "Departure airport and Destination Airport can't be same");
            model.addAttribute("airports", airportService.getAllAirports());
            return "secured/flight/bookFlight";

        }

        List<FlightDto> flights = flightService.FlightsWithDepDateFromDepCityToArrCity(departureDate, departurAirport, arrivalAirport);
        if (flights.isEmpty()) {

            model.addAttribute("notFound", "No record found");

        } else {

            model.addAttribute("flights", flights);
        }

        model.addAttribute("airports", airportService.getAllAirports());
        return "secured/flight/bookFlight";
    }


    @GetMapping("/flight/delete")
    public String deleteFlight(@PathParam("flightId") Long flightId, Model model) {

        flightService.cancelFlight(flightId);
        model.addAttribute("flights", flightService.getAllFlightsPaged(0));
        model.addAttribute("currentPage", "0");
        return "secured/flight/flights";

    }

}
