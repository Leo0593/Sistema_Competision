package org.example.proyecto_competicion.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    // Método reutilizable para enviar correos de confirmación
    public void enviarCorreoConfirmacionIndividual(String correoParticipante, String nombreParticipante, String nombreCompetencia) {
        String asunto = "Registro Exitoso en la Competencia";
        String cuerpo = "Hola " + nombreParticipante + ",\n\n" +
                "Tu inscripción en la competencia '" + nombreCompetencia + "' ha sido registrada exitosamente.\n\n" +
                "¡Te esperamos en el evento!\n\nSaludos,\nEquipo Organizador";

        enviarCorreo(correoParticipante, asunto, cuerpo);
    }

    public void enviarCorreoConfirmacionGrupal(String correoParticipante, String nombreUsuario, String nombreCompetencia) {
        String subject = "Registro Exitoso en la Competencia: " + nombreCompetencia;
        String message = "Hola " + correoParticipante + ",\n\n" +
                "Tu inscripción en la competencia '" + nombreCompetencia + "' ha sido registrada exitosamente.\n\n" +
                "¡Te esperamos en el evento!\n\n" +
                "Este correo fue enviado a ti porque eres parte de un equipo inscrito por " + nombreUsuario + ".\n\n" +
                "Saludos,\n" +
                "Equipo Organizador";

        // Aquí enviamos el correo usando el servicio de correo
        enviarCorreo(correoParticipante, subject, message);
    }

    // Método general para enviar correos
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        mensaje.setFrom("tu_correo@gmail.com"); // Opcional, pero recomendado
        mailSender.send(mensaje);
    }
}