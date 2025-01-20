package org.example.proyecto_competicion.Controllers;

import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Models.Inscripcion;
import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.example.proyecto_competicion.Repository.InscripcionRepository;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CompeticionRepository competicionRepository;  // Para obtener las competiciones disponibles

    @Autowired
    private UsuarioRepository usuarioRepository;  // Para obtener el usuario logueado

    @GetMapping("/all")
    public String getAllInscripciones(Model model) {
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        model.addAttribute("inscripciones", inscripciones);
        return "layout/inscripcion_pages/inscripciones";
    }

    @GetMapping("/add/{competenciaId}")
    public String showForm(@PathVariable("competenciaId") int competenciaId, Model model, Principal principal) {
        // Obtener la competencia seleccionada
        Competicion competicion = competicionRepository.findById(competenciaId).orElse(null);

        if (competicion == null) {
            return "redirect:/inscripcion/all?error=competenciaNotFound";
        }

        // Verificar el tipo de competencia y seleccionar el formulario correspondiente
        String tipoCompetencia = competicion.getTipo(); // Puede ser "individual" o "grupal"
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCompetencia(competenciaId);

        model.addAttribute("inscripcion", inscripcion);
        model.addAttribute("competencia", competicion);

        if ("individual".equalsIgnoreCase(tipoCompetencia)) {
            return "layout/inscripcion_pages/form_individual";
        } else if ("grupal".equalsIgnoreCase(tipoCompetencia)) {
            return "layout/inscripcion_pages/form_grupal";
        } else {
            return "redirect:/inscripcion/all?error=invalidCompetenciaType";
        }
    }

    @PostMapping("/add")
    public String saveInscripcion(@ModelAttribute("inscripcion") Inscripcion inscripcion, Principal principal) {
        // Obtener el correo del usuario logueado
        String correo = principal.getName();

        // Buscar el usuario
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        if (usuario == null) {
            return "redirect:/inscripcion/add?error=userNotFound";
        }

        // Buscar la competencia para validar el tipo
        Competicion competicion = competicionRepository.findById(inscripcion.getCompetencia()).orElse(null);
        if (competicion == null) {
            return "redirect:/inscripcion/add?error=competenciaNotFound";
        }

        // Validar inscripciones según el tipo
        String tipoCompetencia = competicion.getTipo();
        if ("individual".equalsIgnoreCase(tipoCompetencia)) {
            // Validaciones para competencias individuales
            inscripcion.setEnEquipo((byte) 0); // No es en equipo
            inscripcion.setNombreEquipo(usuario.getNombre()); // Asignar el nombre del usuario como nombre del equipo
            inscripcion.setCorreoParticipantes(usuario.getCorreo()); // Asignar el correo del usuario

        } else if ("grupal".equalsIgnoreCase(tipoCompetencia)) {
            // Validaciones para competencias grupales
            if (inscripcion.getNombreEquipo() == null || inscripcion.getNombreEquipo().isEmpty()) {
                return "redirect:/inscripcion/add?error=nombreEquipoRequired";
            }
            if (inscripcion.getCorreoParticipantes() == null || inscripcion.getCorreoParticipantes().isEmpty()) {
                return "redirect:/inscripcion/add?error=correoParticipantesRequired";
            }
            inscripcion.setEnEquipo((byte) 1); // Es en equipo
        } else {
            return "redirect:/inscripcion/add?error=invalidCompetenciaType";
        }

        // Asignar el ID del usuario logueado
        inscripcion.setUsuario(usuario.getId());

        // Si el pago se ha realizado, actualizar la fecha de pago
        if (inscripcion.getPagoRealizado() != null && inscripcion.getPagoRealizado() == 1) {
            inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis()));
        }

        // Establecer las fechas de creación y actualización
        inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Guardar la inscripción
        inscripcionRepository.save(inscripcion);

        return "Inicio";
    }
}