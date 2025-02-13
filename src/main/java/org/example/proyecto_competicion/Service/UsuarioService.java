package org.example.proyecto_competicion.Service;

import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Para encriptar contraseñas

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Optional<Usuario> findByResetToken(String token) {
        return usuarioRepository.findByResetToken(token);
    }

    public void guardarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setContrasena(passwordEncoder.encode(nuevaContrasena)); // Encripta la contraseña
        usuario.setResetToken(null); // Elimina el token después del uso
        usuarioRepository.save(usuario);
    }
}