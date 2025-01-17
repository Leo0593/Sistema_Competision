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

    // Mostrar el formulario de inscripción
    @GetMapping("/add/{competenciaId}")
    public String showForm(@PathVariable("competenciaId") int competenciaId, Model model, Principal principal) {
        // Obtener todas las competiciones
        List<Competicion> competiciones = competicionRepository.findAll();
        model.addAttribute("competiciones", competiciones);

        // Crear un nuevo objeto de inscripción
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setCompetencia(competenciaId);  // Establecer la competencia que se está inscribiendo
        model.addAttribute("inscripcion", inscripcion);

        return "layout/inscripcion_pages/inscripcion_form"; // Ruta de la vista
    }

    // Guardar la inscripción
    @PostMapping("/add")
    public String saveInscripcion(@ModelAttribute("inscripcion") Inscripcion inscripcion, Principal principal) {
        // Obtener el correo del usuario logueado
        String correo = principal.getName();

        // Buscar el usuario
        Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);

        // Si el usuario no existe, redirigir con error
        if (usuario == null) {
            return "redirect:/inscripcion/add?error=userNotFound";
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

        // Redirigir a la vista de inscripciones
        return "redirect:/inscripcion/all";
    }
}