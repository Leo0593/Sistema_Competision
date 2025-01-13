package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {
}