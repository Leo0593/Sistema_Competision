package org.example.proyecto_competicion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Esto marca la clase como una clase de configuración de Spring
public class SecurityConfig {

    // Aquí definimos el bean que Spring utilizará para la codificación de contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Devuelve una nueva instancia de BCryptPasswordEncoder
    }
}
