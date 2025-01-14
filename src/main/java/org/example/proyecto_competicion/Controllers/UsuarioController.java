package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
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

    @GetMapping("/edit/{id}")
    public String editUsuarioForm(@PathVariable("id") int id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuario/all"; // Redirige si el usuario no existe
        }
        model.addAttribute("usuario", usuario);
        return "layout/usuario_pages/editusuario"; // Asegúrate que esta vista existe
    }

    @PostMapping("/edit/{id}")
    public String updateUsuario(@PathVariable("id") int id, @ModelAttribute Usuario updatedUsuario) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) {
            return "redirect:/usuario/all"; // Redirige si el usuario no existe
        }

        // Actualizamos los campos del usuario
        usuario.setNombre(updatedUsuario.getNombre());
        usuario.setApellido(updatedUsuario.getApellido());
        usuario.setCorreo(updatedUsuario.getCorreo());
        usuario.setRol(updatedUsuario.getRol());
        usuario.setEstado(updatedUsuario.getEstado());
        usuario.setHistorial(updatedUsuario.getHistorial());
        usuario.setAvatar(updatedUsuario.getAvatar());
        usuario.setUpdatedAt(new Timestamp(System.currentTimeMillis())); // Actualiza la fecha de modificación

        usuarioRepository.save(usuario); // Guarda el usuario actualizado en la BD

        return "redirect:/usuario/all"; // Redirige de nuevo a la lista de usuarios
    }


}