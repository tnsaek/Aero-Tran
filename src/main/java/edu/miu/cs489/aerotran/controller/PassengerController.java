package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.dto.PassengerDto;
import edu.miu.cs489.aerotran.entity.Flight;
import edu.miu.cs489.aerotran.entity.Passenger;
import edu.miu.cs489.aerotran.service.IFlightService;
import edu.miu.cs489.aerotran.service.IPassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class PassengerController {

    private final IPassengerService passengerService;
    private final IFlightService flightService;

    @GetMapping("/passengers")
    public ModelAndView showPassengersList(@RequestParam("flightId") Long flightId) {

        ModelAndView modelAndView = new ModelAndView("secured/passenger/passenger");

        List<PassengerDto> passengers = passengerService.getAllPassengers();
        modelAndView.addObject("passengers", passengers);

        FlightDto flight = flightService.getFlight(flightId);
            modelAndView.addObject("flight", flight);

        return modelAndView;
    }

    @GetMapping("/flight/book/new")
    public ModelAndView customerInfoPage(@RequestParam("flightId") Long flightId) {

        ModelAndView modelAndView = new ModelAndView("secured/passenger/newPassenger");
        modelAndView.addObject("flightId", flightId);
        modelAndView.addObject("passenger", new Passenger());
        return modelAndView;
    }

    @PostMapping("/flight/book/new")
    public ModelAndView bookFlight(@Valid @ModelAttribute PassengerDto passengerDto, BindingResult bindingResult,
                             @RequestParam("flightId") Long flightId) {
        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.setViewName("secured/passenger/newPassenger");
            return modelAndView;
        }

        FlightDto flightDto = flightService.getFlight(flightId);
        passengerDto.setFlight(flightDto);
        passengerService.addPassenger(passengerDto);

        modelAndView.addObject("passenger", passengerDto);
        modelAndView.setViewName("secured/booking/confirmationPage");

        return modelAndView;
    }

    @GetMapping("/flight/book/verify")
    public ModelAndView showVerifyBookingPage() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("secured/booking/verifyBooking");
        return modelAndView;
    }

    @PostMapping("/flight/book/verify")
    public ModelAndView showVerifyBookingPageResult(@RequestParam("flightId") Long flightId,
                                              @RequestParam("passengerId") Long passengerId) {

        ModelAndView modelAndView = new ModelAndView("secured/booking/verifyBooking");
        FlightDto flight = flightService.getFlight(flightId);

        if (flight != null) {
            modelAndView.addObject("flight", flight);
            List<PassengerDto> passengers = flight.getPassengers();
            PassengerDto passenger = passengers.stream()
                    .filter(p -> p.getPassengerId().equals(passengerId))
                    .findFirst()
                    .orElse(null);

            if (passenger != null) {
                passenger = passengerService.getPassenger(passengerId);
                modelAndView.addObject("passenger", passenger);
            } else {
                modelAndView.addObject("notFound", "Passenger Not Found");
            }
        } else {
            modelAndView.addObject("notFound", "Flight Not Found");
        }
        return modelAndView;

    }

    @PostMapping("/flight/book/cancel")
    public ModelAndView cancelTicket(@RequestParam("passengerId") Long passengerId) {

        passengerService.deletePassenger(passengerId);
        ModelAndView modelAndView = new ModelAndView("secured/flight/flights");
        modelAndView.addObject("flights", flightService.getAllFlightsPaged(0));
        modelAndView.addObject("currentPage", 0);
        return modelAndView;
    }

}
