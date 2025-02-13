package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/recuperar")
public class RecuperarContrasenaController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmailService emailService;

    // Muestra el formulario donde el usuario ingresa su correo
    @GetMapping("/password")
    public String mostrarFormularioRecuperacion() {
        return "layout/login_pages/recuperar_password";
    }

    // Envía un correo con el enlace de recuperación
    @PostMapping("/password")
    public String enviarCorreoRecuperacion(@RequestParam String correo, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findByCorreo(correo);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String token = UUID.randomUUID().toString();
            usuario.setResetToken(token);
            usuarioService.guardarUsuario(usuario); // Guardar el usuario con el token

            String link = "http://localhost:8082/recuperar/nueva?token=" + token;
            emailService.enviarCorreo(correo, "Recuperación de contraseña",
                    "Haz clic en el siguiente enlace para restablecer tu contraseña: " + link);

            model.addAttribute("mensaje", "Correo enviado. Revisa tu bandeja de entrada.");
        } else {
            model.addAttribute("error", "No se encontró una cuenta con ese correo.");
        }

        return "layout/login_pages/recuperar_password";
    }

    // Muestra el formulario donde el usuario ingresa su nueva contraseña
    @GetMapping("/nueva")
    public String mostrarFormularioNuevaContrasena(@RequestParam String token, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findByResetToken(token);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "El enlace es inválido o ha expirado.");
            return "error"; // Podrías hacer una página de error más amigable
        }

        model.addAttribute("token", token);
        return "layout/login_pages/nueva_contrasena";
    }

    // Procesa la nueva contraseña ingresada por el usuario
    @PostMapping("/nueva")
    public String cambiarContrasena(@RequestParam String token, @RequestParam String nuevaContrasena, Model model) {
        Optional<Usuario> usuarioOpt = usuarioService.findByResetToken(token);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "El enlace es inválido o ha expirado.");
            return "error";
        }

        Usuario usuario = usuarioOpt.get();
        usuarioService.actualizarContrasena(usuario, nuevaContrasena);

        model.addAttribute("mensaje", "Tu contraseña ha sido restablecida con éxito. Inicia sesión.");
        return "redirect:/login"; // Redirigir al login después del cambio de contraseña
    }
}
