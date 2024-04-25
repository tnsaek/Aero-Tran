package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.FlightDto;
import edu.miu.cs489.aerotran.dto.PassengerDto;
import edu.miu.cs489.aerotran.entity.Passenger;
import edu.miu.cs489.aerotran.service.IFlightService;
import edu.miu.cs489.aerotran.service.IPassengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PassengerController {

    private final IPassengerService passengerService;
    private final IFlightService flightService;


    @GetMapping("/flight/book/new")
    public String customerInfoPage(@RequestParam("flightId") Long flightId, Model model) {

        model.addAttribute("flightId", flightId);
        model.addAttribute("passenger", new Passenger());
        return "secured/passenger/newPassenger";
    }

    @PostMapping("/flight/book/new")
    public String bookFlight(@Valid @ModelAttribute PassengerDto passengerDto, BindingResult bindingResult,
                             @RequestParam("flightId") Long flightId, Model model) {

        FlightDto flightDto = flightService.getFlight(flightId);
        PassengerDto passenger1 = passengerDto;
        passenger1.setFlight(flightDto);
        passengerService.addPassenger(passenger1);
        model.addAttribute("passenger", passenger1);
        return "secured/booking/confirmationPage";
    }

    @GetMapping("/flight/book/verify")
    public String showVerifyBookingPage() {

        return "secured/booking/verifyBooking";
    }

    @PostMapping("/flight/book/verify")
    public String showVerifyBookingPageResult(@RequestParam("flightId") Long flightId,
                                              @RequestParam("passengerId") Long passengerId, Model model) {

        FlightDto flight = flightService.getFlight(flightId);
        if (flight != null) {

            model.addAttribute("flight", flight);
            List<PassengerDto> passengers = flight.getPassengers();
            PassengerDto passenger = null;
            for (PassengerDto p : passengers) {

                if (p.getPassengerId().equals(passengerId)) {
                    passenger = passengerService.getPassenger(passengerId);
                    model.addAttribute("passenger", passenger);
                }
            }
            if (passenger != null) {

                return "secured/booking/verifyBooking";
            } else {

                model.addAttribute("notFound", "Not Found");
                return "secured/booking/verifyBooking";
            }
        } else {

            model.addAttribute("notFound", "Not Found");
            return "secured/booking/verifyBooking";
        }
    }

    @PostMapping("/flight/book/cancel")
    public String cancelTicket(@RequestParam("passengerId") Long passengerId, Model model) {

        passengerService.deletePassenger(passengerId);
        model.addAttribute("flights", flightService.getAllFlightsPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/flight/flights";
    }

}
