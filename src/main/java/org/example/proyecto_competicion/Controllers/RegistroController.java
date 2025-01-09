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

import java.sql.Timestamp;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private UsuariosRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inyectamos PasswordEncoder

    @GetMapping("/addusuario")
    public String addUsuarioForm(Model model) {
        model.addAttribute("usuario", new Usuarios());  // Cambiado para usar la clase correcta
        return "layout/registro"; // Vista del formulario
    }

    @PostMapping("/addusuario")
    public String addUsuarioSubmit(@ModelAttribute("usuario") Usuarios usuario, Model model) {  // Cambié el tipo a 'Usuarios'
        // Validar si el correo ya está registrado
        if (usuarioRepository.findByCorreo(usuario.getCorreo()) != null) {  // Cambié de 'email' a 'correo'
            model.addAttribute("error", "El correo ya está registrado.");
            return "layout/registro";
        }

        // Hashear la contraseña antes de guardar
        String hashedPassword = passwordEncoder.encode(usuario.getContrasena());  // Cambié 'getPassword' a 'getContrasena'
        usuario.setContrasena(hashedPassword);

        usuario.setFechaInscripcion(new java.sql.Date(System.currentTimeMillis()));  // Ajuste a `Date`
        usuarioRepository.save(usuario);
        model.addAttribute("mensaje", "Usuario registrado exitosamente");
        return "layout/login";
    }
}
