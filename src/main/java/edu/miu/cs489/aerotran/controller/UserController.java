package edu.miu.cs489.aerotran.controller;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
        request.getSession().invalidate();
        return "public/login";
    }
}
