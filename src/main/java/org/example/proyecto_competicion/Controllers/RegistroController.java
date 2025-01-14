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

import java.sql.Timestamp;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/addusuario")
    public String addUsuarioForm(Model model) {
        model.addAttribute("Usuario", new Usuario());
        return "layout/login_pages/registro"; // Vista del formulario
    }

    @PostMapping("/addusuario")
    public String addUsuarioSubmit(@ModelAttribute Usuario usuario, Model model) {

        // Verificar si el correo ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            model.addAttribute("error", "El correo ya está registrado");
            return "layout/login_pages/registro";
        }

        // Cifrar la contraseña antes de guardarla
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Configurar valores predeterminados (estado, fechas, etc.)
        usuario.setEstado((byte) 1); // Activo
        usuario.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        usuario.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        usuario.setRol("usuario"); // Asignamos el rol por defecto como "usuario"
        // Guardar usuario en la base de datos
        usuarioRepository.save(usuario);

        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "layout/login_pages/login"; // Redirigir o mostrar mensaje
    }
}


