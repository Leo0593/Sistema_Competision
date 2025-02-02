package org.example.proyecto_competicion.Service;

import org.example.proyecto_competicion.Models.Inscripcion;
import org.example.proyecto_competicion.Repository.InscripcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InscripcionService {
    @Autowired
    private InscripcionRepository inscripcionRepository;

    public List<Inscripcion> obtenerInscritosPorCompetencia(int competenciaId) {
        return inscripcionRepository.findByCompetencia(competenciaId);
    }
}
