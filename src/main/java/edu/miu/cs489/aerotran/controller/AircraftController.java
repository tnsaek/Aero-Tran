package edu.miu.cs489.aerotran.controller;

import edu.miu.cs489.aerotran.dto.AircraftDto;
import edu.miu.cs489.aerotran.service.IAircraftService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AircraftController {

    private final IAircraftService aircraftService;

    @GetMapping("/aircrafts")
    public String showAircraftsList(@RequestParam(defaultValue = "0") int pageNo, Model model) {

        model.addAttribute("aircraft", aircraftService.getAllAircraftsPaged(pageNo));
        model.addAttribute("currentPage", pageNo);
        return "secured/aircraft/aircraft";
    }

    @GetMapping("/aircraft/new")
    public String showAddAircraftPage(Model model) {

        model.addAttribute("aircraft", new AircraftDto());
        return "secured/aircraft/newAircraft";
    }

    @PostMapping("/aircraft/new")
    public String saveAircraft(@Valid @ModelAttribute("aircraft") AircraftDto aircraftDto, Model model,
                               BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("aircraft", new AircraftDto());
            return "secured/aircraft/newAircraft";
        }

        aircraftService.addAircraft(aircraftDto);
        model.addAttribute("aircraft", aircraftService.getAllAircraftsPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/aircraft/aircraft";

    }

    @GetMapping("/aircraft/delete")
    public String deleteAircraft(@PathParam("aircraftId") Long aircraftId, Model model) {

        aircraftService.deleteAircraftById(aircraftId);
        model.addAttribute("aircraft", aircraftService.getAllAircraftsPaged(0));
        model.addAttribute("currentPage", 0);
        return "secured/aircraft/aircraft";

    }
}
