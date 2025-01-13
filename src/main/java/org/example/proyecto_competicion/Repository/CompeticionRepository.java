package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Integer> {
}