package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNombre(String nombre); // Método para buscar por nombre
}