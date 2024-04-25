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
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class AircraftController {

    private final IAircraftService aircraftService;

    @GetMapping("/aircrafts")
    public ModelAndView showAircraftsList(@RequestParam(defaultValue = "0") int pageNo) {

        ModelAndView modelAndView = new ModelAndView("secured/aircraft/aircraft");
        modelAndView.addObject("aircraft", aircraftService.getAllAircraftsPaged(pageNo));
        modelAndView.addObject("currentPage", pageNo);
        return modelAndView;

    }

    @GetMapping("/aircraft/new")
    public ModelAndView showAddAircraftPage() {

        ModelAndView modelAndView = new ModelAndView("secured/aircraft/newAircraft");
        modelAndView.addObject("aircraft", new AircraftDto());
        return modelAndView;

    }

    @PostMapping("/aircraft/new")
    public ModelAndView saveAircraft(@Valid @ModelAttribute("aircraft") AircraftDto aircraftDto,
                                     BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errors", bindingResult.getAllErrors());
            modelAndView.addObject("aircraft", new AircraftDto());
            modelAndView.setViewName("secured/aircraft/newAircraft");
            return modelAndView;
        }

        aircraftService.addAircraft(aircraftDto);
        modelAndView.addObject("aircraft", aircraftService.getAllAircraftsPaged(0));
        modelAndView.addObject("currentPage", 0);
        modelAndView.setViewName("secured/aircraft/aircraft");
        return modelAndView;

    }

    @GetMapping("/aircraft/delete")
    public ModelAndView deleteAircraft(@PathParam("aircraftId") Long aircraftId) {

        aircraftService.deleteAircraftById(aircraftId);
        ModelAndView modelAndView = new ModelAndView("secured/aircraft/aircraft");
        modelAndView.addObject("aircraft", aircraftService.getAllAircraftsPaged(0));
        modelAndView.addObject("currentPage", 0);
        return modelAndView;

    }
}
