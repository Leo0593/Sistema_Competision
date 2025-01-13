package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Puntuaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuntuacionesRepository extends JpaRepository<Puntuaciones, Integer> {
}