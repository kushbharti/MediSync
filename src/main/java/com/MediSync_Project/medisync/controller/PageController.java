package com.MediSync_Project.medisync.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register"; // This will load `register.html` from `src/main/resources/templates/`
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // This will load `login.html`
    }
}
