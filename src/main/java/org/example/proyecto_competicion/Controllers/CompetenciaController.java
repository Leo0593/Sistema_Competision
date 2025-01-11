package org.example.proyecto_competicion.Controllers;


import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competencia")
public class CompetenciaController {

    @Autowired
    private CompeticionRepository competicionRepository;

    @GetMapping("/all")
    public String getAllCompeticiones(Model model) {
        List<Competicion> competiciones = competicionRepository.findAll();
        model.addAttribute("competiciones", competiciones);
        return "llibres";
    }

    @GetMapping("/add")
    public String addCompeticion(Model model) {
        model.addAttribute("competicion", new Competicion());
        return "addCompeticion";
    }


}
