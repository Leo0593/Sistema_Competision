package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}