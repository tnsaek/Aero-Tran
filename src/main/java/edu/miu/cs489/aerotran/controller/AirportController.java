package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.service.IAirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class AirportController {

    private final IAirportService airportService;

    @GetMapping("/airports")
    public ModelAndView showAirportsList(@RequestParam(defaultValue = "0") int pageNo) {

        ModelAndView modelAndView = new ModelAndView("secured/airport/airport");
        AirportDto airportDto = new AirportDto();
        modelAndView.addObject("address", airportDto.getAddress());
        modelAndView.addObject("airports", airportService.getAllAirportPaged(pageNo));
        modelAndView.addObject("currentPage", pageNo);
        return modelAndView;

    }

    @GetMapping("/airport/new")
    public ModelAndView showAddAirportPage() {

        ModelAndView modelAndView = new ModelAndView("secured/airport/newAirport");
        modelAndView.addObject("airport", new Airport());
        return modelAndView;

    }

    @PostMapping("/airport/new")
    public ModelAndView saveAirport(@Valid @ModelAttribute("airport") AirportDto airportDto, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("secured/airport/newAirport");
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.addObject("address", airportDto.getAddress());
            modelAndView.addObject("airports", new AirportDto());
            return modelAndView;
        }

        airportService.addAirport(airportDto);
        modelAndView.setViewName("secured/airport/airport");
        modelAndView.addObject("airports", airportService.getAllAirportPaged(0));
        modelAndView.addObject("currentPage", 0);

        return modelAndView;

    }

    @GetMapping("/airport/delete")
    public ModelAndView deleteAirport(@RequestParam("airportId") Long airportId) {

        airportService.deleteAirport(airportId);
        ModelAndView modelAndView = new ModelAndView("secured/airport/airport");
        modelAndView.addObject("airports", airportService.getAllAirportPaged(0));
        modelAndView.addObject("currentPage", 0);

        return modelAndView;

    }
}
