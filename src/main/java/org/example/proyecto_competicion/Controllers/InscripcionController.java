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

    @Autowired
    private EmailService emailService;

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
    public String showForm(@PathVariable("competenciaId") int competenciaId,
                           Model model,
                           Principal principal) {
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
        int precio = competicion.getPrecioInscripcion();
        if ("individual".equalsIgnoreCase(tipoCompetencia) && precio > 0) {
            return "layout/inscripcion_pages/form_individual_pago";
        } else if ("individual".equalsIgnoreCase(tipoCompetencia) && precio == 0) {
            return "layout/inscripcion_pages/form_individual_gratis";
        } else if ("grupal".equalsIgnoreCase(tipoCompetencia) && precio > 0) {
            return "layout/inscripcion_pages/form_grupal_pago";
        } else if ("grupal".equalsIgnoreCase(tipoCompetencia) && precio == 0) {
            return "layout/inscripcion_pages/form_grupal_gratis";
        } else {
            return "redirect:/inscripcion/all?error=invalidCompetenciaType";
        }
    }

    // METODOS PARA GUARDAR UNA INSCRIPCION GRATUITA//

    @PostMapping("/add_individual_gratis")
    public String inscripcion_individual_gratis(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @RequestParam("correoParticipantes") String correoParticipantes, // Aquí recibimos el correo ingresado en el formulario
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

        // Guardar la inscripción en la base de datos
        inscripcion.setUsuario(usuario.getId());
        inscripcion.setCompetencia(competicion.getId());
        inscripcion.setEnEquipo((byte) 0);
        inscripcion.setNombreEquipo(usuario.getNombre());
        inscripcion.setPagoRealizado((byte) 1);
        inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis()));
        inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        inscripcionRepository.save(inscripcion);

        // Enviar correo de confirmación al correo del formulario
        emailService.enviarCorreoConfirmacionIndividual(
                correoParticipantes, // Correo del participante que ingresó en el formulario
                usuario.getNombre(), // Nombre del usuario
                competicion.getNombre() // Nombre de la competencia
        );

        // Redirigir con éxito
        return "redirect:/competencia/all?success=true"; // Incluimos el parámetro success=true
    }

    @PostMapping("/add_grupal_gratis")
    public String inscripcion_grupal_gratis(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @RequestParam("correoParticipantes") String correoParticipantes, // Aquí recibimos los correos ingresados en el formulario
            @RequestParam("nombreEquipo") String nombreEquipo, // Capturamos el nombre del equipo desde el formulario
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

        // Guardar la inscripción
        inscripcion.setUsuario(usuario.getId());
        inscripcion.setCompetencia(competicion.getId());
        inscripcion.setEnEquipo((byte) 1);
        inscripcion.setPagoRealizado((byte) 1);
        inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis()));
        inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        inscripcion.setNombreEquipo(nombreEquipo); // Guardar el nombre del equipo
        inscripcionRepository.save(inscripcion);

        // Separar los correos para enviar a todos los participantes
        String[] correosArray = correoParticipantes.split(",");

        // Enviar el correo de confirmación a todos los participantes
        for (String correoParticipante : correosArray) {
            emailService.enviarCorreoConfirmacionGrupal(
                    correoParticipante.trim(), // Correo del participante
                    usuario.getNombre(), // Nombre del usuario autenticado
                    competicion.getNombre() // Nombre de la competencia
            );
        }

        // Redirigir con éxito
        return "redirect:/competencia/all?success=true"; // Incluimos el parámetro success=true
    }

    // METODOS PARA GUARDAR UNA INSCRIPCION PAGADOS CON STRIPE//

    @PostMapping("/add_individual_pago")
    public String inscripcion_individual_pago(
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

            emailService.enviarCorreoConfirmacionIndividual(
                    correoParticipante, // Correo del participante que ingresó en el formulario
                    usuario.getNombre(), // Nombre del usuario
                    competicion.getNombre() // Nombre de la competencia
            );

            redirectAttributes.addFlashAttribute("success", "Inscripción guardada con éxito");


            return "redirect:/competencia/all"; // Redirige a la pantalla de inicio
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al procesar la inscripción");
            return "redirect:/Error";
        }
    }


    @PostMapping("/add_grupal_pago")
    public String inscripcion_gruapl_pago(
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

            String nom_equipo = (String) inscripcionData.get("nombreEquipo");

            Stripe.apiKey = stripeService.getApiSecretKey();
            PaymentIntent paymentIntent = PaymentIntent.retrieve((String) inscripcionData.get("paymentIntentId"));

            if (!"succeeded".equals(paymentIntent.getStatus())) {
                redirectAttributes.addFlashAttribute("error", "Pago fallido");
                return "redirect:/Error";
            }

            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setUsuario(usuario.getId());
            inscripcion.setCompetencia(competicion.getId());
            inscripcion.setEnEquipo((byte) 1);
            inscripcion.setNombreEquipo(nom_equipo);
            inscripcion.setPagoRealizado((byte) 1);
            inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis()));
            inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            inscripcion.setCorreoParticipantes(correoParticipante);

            inscripcionRepository.save(inscripcion);

            // Separar los correos para enviar a todos los participantes
            String[] correosArray = correoParticipante.split(",");

            // Enviar el correo de confirmación a todos los participantes
            for (String CorreoParticipante : correosArray) {
                emailService.enviarCorreoConfirmacionGrupal(
                        CorreoParticipante.trim(), // Correo del participante
                        usuario.getNombre(), // Nombre del usuario autenticado
                        competicion.getNombre() // Nombre de la competencia
                );
            }

            redirectAttributes.addFlashAttribute("success", "Inscripción guardada con éxito");
            return "redirect:/competencia/all"; // Redirige a la pantalla de inicio
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al procesar la inscripción");
            return "redirect:/Error";
        }
    }







}
