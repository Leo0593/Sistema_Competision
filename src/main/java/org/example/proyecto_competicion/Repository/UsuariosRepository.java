package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuariosRepository extends JpaRepository<Usuarios, Integer> {
    Usuarios findByCorreo(String correo);
}