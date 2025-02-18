package org.example.proyecto_competicion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoCompeticionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProyectoCompeticionApplication.class, args);
    }
    /*ajdasdjaklsdjas*/
    /*
    @PostMapping("/add")
    public String saveInscripcion(
            @ModelAttribute("inscripcion") Inscripcion inscripcion,
            @RequestParam("paymentIntentId") String paymentIntentId,
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

        // Verificar el estado del pago en Stripe
        try {
            Stripe.apiKey = stripeService.getApiSecretKey(); // Asegúrate de que el servicio Stripe tenga la clave secreta configurada

            // Consultar el PaymentIntent en Stripe
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

            if (!"succeeded".equals(paymentIntent.getStatus())) {
                // Si el pago no se completó, redirigir con un mensaje de error
                return "redirect:/inscripcion/add?error=paymentFailed";
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

            // Si el pago fue exitoso, guardar información de pago en la inscripción
            inscripcion.setUsuario(usuario.getId());
            inscripcion.setPagoRealizado((byte) 1); // Indicar que el pago se realizó
            inscripcion.setFechaPago(new Timestamp(System.currentTimeMillis())); // Fecha del pago
            inscripcion.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            inscripcion.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            // Guardar la inscripción en la base de datos
            inscripcionRepository.save(inscripcion);

            // Redirigir con éxito
            return "redirect:/inscripcion/all?success=true";

        } catch (Exception e) {
            e.printStackTrace();
            // En caso de error con Stripe, redirigir con un mensaje de error
            return "redirect:/inscripcion/add?error=pagoError";
        }
    }

    */
}
