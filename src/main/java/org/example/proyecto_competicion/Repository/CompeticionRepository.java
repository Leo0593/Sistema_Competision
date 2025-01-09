package org.example.proyecto_competicion.Repository;

import Models.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompeticionRepository extends JpaRepository<Competicion, Integer> {
}