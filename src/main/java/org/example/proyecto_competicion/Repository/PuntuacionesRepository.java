package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Puntuaciones;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PuntuacionesRepository extends JpaRepository<Puntuaciones, Integer> {

    @Query("SELECT p.inscripcionByInscripcionId.usuarioByUsuario.nombre, SUM(p.puntaje) " +
            "FROM Puntuaciones p " +
            "WHERE p.inscripcionByInscripcionId.competencia = :competenciaId " +
            "GROUP BY p.inscripcionByInscripcionId.usuarioByUsuario.id " +
            "ORDER BY SUM(p.puntaje) DESC")
    List<Object[]> obtenerRankingPorCompetencia(int competenciaId);

}