package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Implementa los métodos necesarios para el CRUD de usuarios

    @GetMapping("/all")
    public String getAllUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "layout/usuario_pages/usuarios";  // Asegúrate que esta sea la ruta correcta
    }


}