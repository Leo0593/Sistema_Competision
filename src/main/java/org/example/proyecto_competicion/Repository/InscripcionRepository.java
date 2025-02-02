package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Integer> {

    List<Inscripcion> findByCompetencia(int competencia);

}