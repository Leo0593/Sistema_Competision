package org.example.proyecto_competicion.Service;

import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // Verificar si el estado del usuario es 1 (activado)
        if (usuario.getEstado() != 1) {
            throw new UsernameNotFoundException("Usuario desactivado");
        }
        // Aquí simplemente asignamos la contraseña ya cifrada en la base de datos
        return User.builder()
                .username(usuario.getCorreo())
                .password(usuario.getContrasena()) // Contraseña cifrada ya en la base de datos
                .roles(usuario.getRol()) // Asegúrate de que `usuario.getRol()` sea correcto
                .build();
    }
}
