package org.example.proyecto_competicion.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "index";
    }

    /*@GetMapping("/login")
    public String loginPage() {
        return "layout/login_page/login";
    }*/

}