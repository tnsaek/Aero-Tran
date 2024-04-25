package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.AirportDto;
import edu.miu.cs489.aerotran.entity.Airport;
import edu.miu.cs489.aerotran.service.IAirportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AirportController {

    private final IAirportService airportService;

    @GetMapping("/airports")
    public String showAirportsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {
        AirportDto airportDto = new AirportDto();
        model.addAttribute("address", airportDto.getAddress());
        model.addAttribute("airports", airportService.getAllAirportPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "secured/airport/airport";
    }

    @GetMapping("/airport/new")
    public String showAddAirportPage(Model model) {
        model.addAttribute("airport", new Airport());
        return "secured/airport/newAirport";
    }

    @PostMapping("/airport/new")
    public String saveAirport(@Valid @ModelAttribute("airport") AirportDto airportDto, BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {

            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("address", airportDto.getAddress());
            model.addAttribute("airports", new AirportDto());
            return "secured/airport/newAirport";
        }
        airportService.addAirport(airportDto);
        model.addAttribute("airports", airportService.getAllAirportPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/airport/airport";
    }

    @GetMapping("/airport/delete")
    public String deleteAirport(@RequestParam("airportId") Long airportId, Model model) {

        airportService.deleteAirport(airportId);
        model.addAttribute("airports", airportService.getAllAirportPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/airport/airport";
    }
}
