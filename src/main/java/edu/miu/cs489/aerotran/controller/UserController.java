package edu.miu.cs489.aerotran.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class UserController {

    @GetMapping("")
    public String showHomePage() {
        return "public/home";
    }

    @GetMapping("/login")
    public String showLoginPage() {

        return "public/login";
    }
}
