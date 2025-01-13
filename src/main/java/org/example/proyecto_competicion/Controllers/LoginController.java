package org.example.proyecto_competicion.Controllers;


import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String loginForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "layout/login_pages/login"; // Vista del formulario
    }

    @PostMapping
    public String loginSubmit(@ModelAttribute("usuario") Usuario usuario, Model model) {
        Usuario usuarioExistente = usuarioRepository.findByCorreo(usuario.getCorreo()).orElse(null); // Buscar por email

        if (usuarioExistente != null && passwordEncoder.matches(usuario.getContrasena(), usuarioExistente.getContrasena())) {
            model.addAttribute("mensaje", "Login exitoso. ¡Bienvenido, " + usuarioExistente.getNombre() + "!");
            return "Inicio"; // Redirige a la página principal
        }

        model.addAttribute("error", "Correo o contraseña incorrectos.");
        return "layout/login_pages/login"; // Vuelve al formulario
    }
}
