package org.example.proyecto_competicion.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")  // La ruta base es /login
public class LoginController {

    @GetMapping
    public String loginForm(Model model) {
        return "layout/login_pages/login"; // Muestra la vista de login
    }

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
        // Lógica de login
        return "Inicio"; // Redirige a la página de inicio o alguna otra acción
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        return "layout/menu"; // Muestra la vista de login
    }
}
