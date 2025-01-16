package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.CategoriaRepository;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public String saveCompeticion(@ModelAttribute("competicion") Competicion competicion, Principal principal) {
        // Obtener el correo del usuario logueado desde 'principal'
        String correo = principal.getName();

        // Buscar el usuario en la base de datos por el correo electrónico
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        // Verificar si el usuario existe
        if (usuario == null) {
            return "redirect:/competencia/add?error=userNotFound";
        }

        // Asignar el ID del usuario logueado como el creador de la competición
        competicion.setIdCreador(usuario.getId());

        // Si el tipo es "individual", asignar 1 persona por grupo
        if ("individual".equals(competicion.getTipo())) {
            competicion.setPersonasPorGrupo(1);  // Si es individual, asignamos 1
        }

        // Validación de fechas: la fecha de inicio no debe ser después de la fecha de fin
        LocalDateTime fechaInicio = competicion.getFechaInicio();
        LocalDateTime fechaFin = competicion.getFechaFin();

        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            // Redirigir si las fechas no son válidas
            return "redirect:/competencia/add?error=invalidDates";
        }

        // Configurar otras propiedades de la competición
        competicion.setEstado((byte) 1);
        competicion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        competicion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Guardar la competición
        competicionRepository.save(competicion);

        // Redirigir a la página principal o la vista correspondiente
        return "Inicio";  // O la ruta que corresponda
    }
}
