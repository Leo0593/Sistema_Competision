package org.example.proyecto_competicion.Controllers;


import org.example.proyecto_competicion.Models.Inscripcion;
import org.example.proyecto_competicion.Models.Puntuaciones;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.example.proyecto_competicion.Repository.InscripcionRepository;
import org.example.proyecto_competicion.Repository.PuntuacionesRepository;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.example.proyecto_competicion.Service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/puntuacion")

public class PuntuacionController {

    @Autowired
    private PuntuacionesRepository puntuacionesRepository;

    @Autowired
    private InscripcionService inscripcionService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @GetMapping("/all")
    public String getAllPuntuaciones(Model model) {
        List<Puntuaciones> puntuaciones = puntuacionesRepository.findAll();
        model.addAttribute("puntuaciones", puntuaciones);
        return "layout/puntuacion_pages/puntuaciones";  // Asegúrate que esta sea la ruta correcta
    }

    @GetMapping("/add/{competenciaId}")
    public String mostrarFormularioPuntuacion(@PathVariable int competenciaId, Model model) {
        List<Inscripcion> inscritos = inscripcionService.obtenerInscritosPorCompetencia(competenciaId);
        model.addAttribute("inscritos", inscritos);
        model.addAttribute("puntuacion", new Puntuaciones());
        return "layout/puntuacion_pages/addpuntuacion";  // Asegúrate que esta sea la ruta correcta
    }

    @PostMapping("/save")
    public String guardarPuntuacion(@ModelAttribute Puntuaciones puntuacion) {
        puntuacionesRepository.save(puntuacion);
        return "redirect:/competencia/all"; // Redirige a la pantalla de inicio
    }

    @GetMapping("/edit/{id}")
    public String editPuntuacion(@PathVariable("id") int id, Model model) {
        Puntuaciones puntuacion = puntuacionesRepository.findById(id).orElse(null);
        if (puntuacion == null) {
            return "redirect:/puntuacion/all";  // Redirige si no encuentra la puntuación
        }
        List<Inscripcion> inscritos = inscripcionRepository.findAll();
        model.addAttribute("puntuacion", puntuacion);
        model.addAttribute("inscritos", inscritos);
        return "layout/puntuacion_pages/editpuntuacion";
    }

    // Procesar actualización de puntuación
    @PostMapping("/update")
    public String updatePuntuacion(@ModelAttribute Puntuaciones puntuacion) {
        puntuacionesRepository.save(puntuacion);
        return "redirect:/competencia/all"; // Redirige a la pantalla de inicio
    }

    @GetMapping("/delete/{id}")
    public String deletePuntuacion(@PathVariable("id") int id) {
        puntuacionesRepository.deleteById(id);
        return "redirect:/puntuacion/all"; // Redirige a la pantalla de inicio
    }



}
