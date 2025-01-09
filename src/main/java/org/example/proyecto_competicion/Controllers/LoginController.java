package org.example.proyecto_competicion.Controllers;


import org.example.proyecto_competicion.Models.Usuarios;
import org.example.proyecto_competicion.Repository.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("usuario", new Usuarios());
        return "layout/login"; // Vista del formulario
    }



}
