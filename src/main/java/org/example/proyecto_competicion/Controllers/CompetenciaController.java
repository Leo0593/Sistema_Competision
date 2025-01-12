package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Repository.CategoriaRepository;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Obtener todas las competiciones
    @GetMapping("/all")
    public String getAllCompeticiones(Model model) {
        List<Competicion> competiciones = competicionRepository.findAll();
        model.addAttribute("competiciones", competiciones);
        return "layout/competencia_pages/competiciones";  // Asegúrate que esta sea la ruta correcta
    }

    @GetMapping("/add")
    public String addCompeticion(Model model) {
        Competicion competicion = new Competicion();
        model.addAttribute("competicion", competicion);
        model.addAttribute("categorias", categoriaRepository.findAll()); // Obtener todas las categorías
        return "layout/competencia_pages/addcompeticion";
    }


    @PostMapping("/add")
    public String saveCompeticion(@ModelAttribute("competicion") Competicion competicion) {

        if ("individual".equals(competicion.getTipo())) {
            competicion.setPersonasPorGrupo(1);  // Si es individual, asignamos 1
        }
        // Asegúrate de que las fechas estén correctas
        LocalDateTime fechaInicio = competicion.getFechaInicio();
        LocalDateTime fechaFin = competicion.getFechaFin();

        // Validación de fechas: la fecha de inicio no debe ser después de la fecha de fin
        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            // Redirige o muestra un mensaje de error si las fechas no son válidas
            return "redirect:/competencia/add?error=invalidDates";
        }

        competicion.setEstado((byte) 1);
        competicion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        competicion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        competicionRepository.save(competicion);

        return "Inicio";
    }
}
