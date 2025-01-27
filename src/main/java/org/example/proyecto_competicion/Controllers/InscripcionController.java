package org.example.proyecto_competicion.Controllers;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.example.proyecto_competicion.Models.Competicion;
import org.example.proyecto_competicion.Models.Inscripcion;
import org.example.proyecto_competicion.Models.Usuario;
import org.example.proyecto_competicion.Repository.CompeticionRepository;
import org.example.proyecto_competicion.Repository.InscripcionRepository;
import org.example.proyecto_competicion.Repository.UsuarioRepository;
import org.example.proyecto_competicion.Service.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/inscripcion")
public class InscripcionController {

    @Value("${stripe.keys.public}")
    private String API_PUBLIC_KEY;

    private StripeService stripeService;

    @Autowired
    private InscripcionRepository inscripcionRepository;

    @Autowired
    private CompeticionRepository competicionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public InscripcionController(StripeService stripeService)
    {
        this.stripeService = stripeService;
    }

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
        model.addAttribute("stripePublicKey", API_PUBLIC_KEY);

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
            @RequestBody Map<String, Object> inscripcionData,
            Principal principal,
            RedirectAttributes redirectAttributes
    ) {
        try {
            String correo = principal.getName();
            Usuario usuario = usuarioRepository.findByCorreo(correo).orElse(null);
            if (usuario == null) {
                redirectAttributes.addFlashAttribute("error", "Usuario no encontrado");
                return "redirect:/Error";
            }

            String competenciaIdStr = (String) inscripcionData.get("competenciaId");
            Integer competenciaId = Integer.parseInt(competenciaIdStr);

            Competicion competicion = competicionRepository.findById(competenciaId).orElse(null);
            if (competicion == null) {
                redirectAttributes.addFlashAttribute("error", "Competencia no encontrada");
                return "redirect:/Error";
            }

            String correoParticipante = (String) inscripcionData.get("correoParticipantes");

            Stripe.apiKey = stripeService.getApiSecretKey();
            PaymentIntent paymentIntent = PaymentIntent.retrieve((String) inscripcionData.get("paymentIntentId"));

            if (!"succeeded".equals(paymentIntent.getStatus())) {
                redirectAttributes.addFlashAttribute("error", "Pago fallido");
                return "redirect:/Error";
            }

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setUsuario(usuario.getId());
            inscripcion.setCompetencia(competicion.getId());
            inscripcion.setEnEquipo((byte) 0);
            inscripcion.setNombreEquipo(usuario.getNombre());
            inscripcion.setPagoRealizado((byte) 1);
            inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis()));
            inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            inscripcion.setCorreoParticipantes(correoParticipante);

            inscripcionRepository.save(inscripcion);

            redirectAttributes.addFlashAttribute("success", "Inscripción guardada con éxito");
            return "redirect:/competencia/all"; // Redirige a la pantalla de inicio
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al procesar la inscripción");
            return "redirect:/Error";
        }
    }



}
