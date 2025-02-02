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
        return "redirect:/competencia/all";  // O la ruta que corresponda
    }

    @GetMapping("/edit/{id}")
    public String editCompeticion(@PathVariable("id") int id, Model model) {
        // Buscar la competición por su ID
        Competicion competicion = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicion == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Agregar la competición y las categorías disponibles al modelo
        model.addAttribute("competicion", competicion);
        model.addAttribute("categorias", categoriaRepository.findAll()); // Obtener todas las categorías
        return "layout/competencia_pages/editcompeticion";
    }

    @PostMapping("/edit/{id}")
    public String updateCompeticion(@PathVariable("id") int id, @ModelAttribute("competicion") Competicion competicion) {
        // Buscar la competición por su ID
        Competicion competicionActual = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicionActual == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Validación de fechas: la fecha de inicio no debe ser después de la fecha de fin
        LocalDateTime fechaInicio = competicion.getFechaInicio();
        LocalDateTime fechaFin = competicion.getFechaFin();

        if (fechaInicio == null || fechaFin == null || fechaInicio.isAfter(fechaFin)) {
            return "redirect:/competencia/all?error=invalidDates";
        }

        // Actualizar las propiedades de la competición
        competicionActual.setNombre(competicion.getNombre());
        competicionActual.setDescripcion(competicion.getDescripcion());
        competicionActual.setFechaInicio(fechaInicio);
        competicionActual.setFechaFin(fechaFin);
        competicionActual.setIdCategoria(competicion.getIdCategoria());

        competicionActual.setTipo(competicion.getTipo());

        if ("individual".equals(competicion.getTipo())) {
            competicionActual.setPersonasPorGrupo(1);
        } else {
            competicionActual.setPersonasPorGrupo(competicion.getPersonasPorGrupo());
        }

        competicionActual.setPrecioInscripcion(competicion.getPrecioInscripcion());

        // Actualizar el ID de la categoría (suponiendo que 'idCategoria' es un campo en Competicion)
        competicionActual.setEstado(competicion.getEstado());
        competicionActual.setUbicacion(competicion.getUbicacion());

        competicionActual.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        // Guardar los cambios
        competicionRepository.save(competicionActual);

        // Redirigir a la página correspondiente después de la actualización
        return "redirect:/competencia/all";  // O la ruta que corresponda, puede ser la vista de la competición
    }

    @GetMapping("/delete/{id}")
    public String deleteCompeticion(@PathVariable("id") int id) {
        // Buscar la competición por su ID
        Competicion competicion = competicionRepository.findById(id).orElse(null);

        // Si no existe la competición, redirigir con un mensaje de error
        if (competicion == null) {
            return "redirect:/competencia/all?error=notFound";
        }

        // Eliminar la competición
        competicionRepository.delete(competicion);

        // Redirigir a la página principal o la vista correspondiente
        return "redirect:/competencia/all";  // O la ruta que corresponda
    }




}
