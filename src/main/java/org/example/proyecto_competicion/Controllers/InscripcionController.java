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

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/inscripcion")
public class InscripcionController {

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @GetMapping("/all")
    public String getAllInscripciones(Model model) {
        List<Inscripcion> inscripciones = inscripcionRepository.findAll();
        model.addAttribute("inscripciones", inscripciones);
        return "layout/inscripcion_pages/inscripciones";
    }

    @GetMapping("/add/{competenciaId}")
    public String showForm(@PathVariable("competenciaId") int competenciaId, Model model, Principal principal) {
        Competicion competicion = competicionRepository.findById(competenciaId).orElse(null);

        if (competicion == null) {
            return "redirect:/inscripcion/all?error=competenciaNotFound";
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCompetencia(competenciaId);

        model.addAttribute("inscripcion", inscripcion);
        model.addAttribute("competencia", competicion);

        String tipoCompetencia = competicion.getTipo();
        if ("individual".equalsIgnoreCase(tipoCompetencia)) {
            return "layout/inscripcion_pages/form_individual";
        } else if ("grupal".equalsIgnoreCase(tipoCompetencia)) {
            return "layout/inscripcion_pages/form_grupal";
        } else {
            return "redirect:/inscripcion/all?error=invalidCompetenciaType";
        }
    }

    @PostMapping("/add")
    public String saveInscripcion(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @RequestParam("stripeToken") String token,
            Principal principal
    ) {
        // Obtener el usuario autenticado
        String correo = principal.getName();
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
        if (usuario == null) {
            return "redirect:/inscripcion/add?error=userNotFound";
        }

        // Buscar la competencia asociada
        Competicion competicion = competicionRepository.findById(inscripcion.getCompetencia()).orElse(null);
        if (competicion == null) {
            return "redirect:/inscripcion/add?error=competenciaNotFound";
        }

        // Configuración según el tipo de competencia
        String tipoCompetencia = competicion.getTipo();
        if ("individual".equalsIgnoreCase(tipoCompetencia)) {
            // Competencia individual
            inscripcion.setEnEquipo((byte) 0);
            inscripcion.setNombreEquipo(usuario.getNombre());
            inscripcion.setCorreoParticipantes(usuario.getCorreo());
        } else if ("grupal".equalsIgnoreCase(tipoCompetencia)) {
            // Competencia grupal
            if (inscripcion.getNombreEquipo() == null || inscripcion.getNombreEquipo().isEmpty()) {
                return "redirect:/inscripcion/add?error=nombreEquipoRequired";
            }
            if (inscripcion.getCorreoParticipantes() == null || inscripcion.getCorreoParticipantes().isEmpty()) {
                return "redirect:/inscripcion/add?error=correoParticipantesRequired";
            }
            inscripcion.setEnEquipo((byte) 1);
        } else {
            return "redirect:/inscripcion/add?error=invalidCompetenciaType";
        }





        // Guardar información de pago en la inscripción
        inscripcion.setUsuario(usuario.getId());
        inscripcion.setPagoRealizado((byte) 1); // Indicar que el pago se realizó
        inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis())); // Fecha del pago
        inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        // Guardar la inscripción en la base de datos
        inscripcionRepository.save(inscripcion);

        // Redirigir con éxito
        return "redirect:/inscripcion/all?success=true";
    }



}
