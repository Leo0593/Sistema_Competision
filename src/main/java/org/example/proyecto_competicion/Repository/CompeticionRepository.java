package org.example.proyecto_competicion.Repository;

import org.example.proyecto_competicion.Models.Competicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompeticionRepository extends JpaRepository<Competicion, Integer>, JpaSpecificationExecutor<Competicion> {

    // Obtener todas las competiciones ordenadas por fecha de inicio ascendente
    List<Competicion> findAllByOrderByFechaInicioAsc();

    // Obtener todas las competiciones ordenadas por fecha de inicio descendente
    List<Competicion> findAllByOrderByFechaInicioDesc();

    // Metodo para ordenar por estado(abierto o cerrado)
    List<Competicion> findByEstado(int estado);

    List<Competicion> findByTipo(String tipo);

    // En el repositorio CompeticionRepository
    List<Competicion> findAllByOrderByUbicacionAsc();  // Método para ordenar por localidad (ascendente)
    List<Competicion> findAllByOrderByUbicacionDesc(); // Método para ordenar por localidad (descendente)



}