package org.example.proyecto_competicion.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

@Configuration
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    // Constructor que inyecta el UserDetailsService
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // Bean para codificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Método de autenticación para el AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    // Configuración de la seguridad web
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF solo para pruebas, si no lo necesitas habilítalo

                // Autorización de solicitudes
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/login", "/registro/**", "/css/**", "/img/**").permitAll() // Accesos públicos
                        .requestMatchers("/categoria/**", "/competencia/**", "/usuario/**").hasRole("ADMIN") // Solo ADMIN
                        .requestMatchers("/competencia/all").permitAll() // Todos los usuarios pueden ver esta página
                        .anyRequest().authenticated() // Todo lo demás requiere autenticación
                )

                // Configuración de inicio de sesión
                .formLogin(form -> form
                        .loginPage("/login") // Ruta personalizada para el formulario de login
                        .defaultSuccessUrl("/", true) // Redirige tras un login exitoso
                        .permitAll() // Permitir el acceso a todos al formulario
                )

                // Configuración de logout
                .logout(logout -> logout
                        .logoutUrl("/logout") // Ruta para cerrar sesión
                        .logoutSuccessUrl("/login?logout") // Redirige tras cerrar sesión
                        .permitAll()
                );

        return http.build();
    }
}
