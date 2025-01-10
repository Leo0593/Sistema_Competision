package org.example.proyecto_competicion.Controllers;


import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private CompeticionRepository competicionRepository;

    @GetMapping
    public List<Competicion> getAllCompeticiones() {
        return competicionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Competicion> updateCompeticion(@PathVariable int id, @RequestBody Competicion competicionDetails)  {
        Optional<Competicion> optionalCompeticion = competicionRepository.findById(id);
        if (!optionalCompeticion.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Competicion competicion = optionalCompeticion.get();
        competicion.setNombre(competicionDetails.getNombre());
        competicion.setDescripcion(competicionDetails.getDescripcion());
        competicion.setFechaInicio(competicionDetails.getFechaInicio());
        competicion.setFechaFin(competicionDetails.getFechaFin());
        competicion.setEstado(competicionDetails.getEstado());
        competicion.setMaxParticipantes(competicionDetails.getMaxParticipantes());
        competicion.setIdCreador(competicionDetails.getIdCreador());
        competicion.setFechaCreacion(competicionDetails.getFechaCreacion());
        competicion.setTipo(competicionDetails.getTipo());
        competicion.setCantidad(competicionDetails.getCantidad());

        Competicion updatedCompeticion = competicionRepository.save(competicion);
        return ResponseEntity.ok(updatedCompeticion);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompeticion(@PathVariable int id) {
        if (!competicionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        competicionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
