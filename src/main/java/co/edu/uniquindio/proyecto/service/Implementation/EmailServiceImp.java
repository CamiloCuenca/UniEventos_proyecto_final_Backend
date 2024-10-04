package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {
    private final String SMTP_USERNAME = "unieventosproyect@gmail.com";
    private final String SMTP_PASSWORD = "fyncswwbtqwubuja";

    @Override
    @Async
    public void sendMail(EmailDTO emailDTO) throws Exception {
        Email email = EmailBuilder.startingBlank()
                .from(SMTP_USERNAME)
                .to(emailDTO.recipient())
                .withSubject(emailDTO.issue())
                .withHTMLText(emailDTO.body())
                .buildEmail();

        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, SMTP_USERNAME, SMTP_PASSWORD)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {
            mailer.sendMail(email);
        }
    }

    @Override
    @Async
    public void sendQrByEmail(String email, String qrUrl) {
        String htmlMessage = "<html><body>" +
                "<p>Estimado usuario,</p>" +
                "<p>Gracias por su compra. A continuación encontrará el código QR de su orden:</p>" +
                "<img src=\"" + qrUrl + "\" alt=\"Código QR\" style=\"display:block; max-width:100%; height:auto;\" />" +
                "<p>Atentamente,<br/>El equipo de UniEventos</p>" +
                "</body></html>";

        try {
            sendMail(new EmailDTO(email, "Código QR de su Orden", htmlMessage));
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones, podrías usar un logger aquí
        }
    }

    @Async
    public void sendCodevalidation(String email, String validationCode) throws Exception {

        String plainTextMessage = "Estimado usuario,\n\n" +
                "Gracias por registrarse en nuestra plataforma. Para activar su cuenta, por favor utilice el siguiente código de activación:\n\n" +
                "Código de activación: " + validationCode + "\n\n" +
                "Este código es válido por 15 minutos.\n\n" +
                "Si usted no solicitó este registro, por favor ignore este correo.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        sendMail(new EmailDTO(email, "\"Activación de cuenta\"", plainTextMessage));

    }

    @Override
    public void sendRecoveryCode(String email, String passwordValidationCode) throws Exception {
        // Crear el mensaje a enviar por correo
        String plainTextMessage = "Estimado usuario,\n\n" +
                "Ha solicitado recuperar su contraseña. Utilice el siguiente código de recuperación para restablecer su contraseña:\n\n" +
                "Código de recuperación: " + passwordValidationCode + "\n\n" +
                "Este código es válido por 15 minutos.\n\n" +
                "Si usted no solicitó esta recuperación, por favor ignore este correo.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        // Enviar el correo electrónico con el código de recuperación
        sendMail(new EmailDTO(email, "Recuperación de contraseña", plainTextMessage));
    }

    @Override
    public void sendWelcomeCoupon(String email, String couponCode) throws Exception {
        // Preparar el cuerpo del correo que incluye el código del cupón
        String plainTextMessage = "Estimado usuario,\n\n" +
                "Gracias por registrarse en nuestra plataforma. Para celebrar su registro, le ofrecemos un cupón de bienvenida con un 15% de descuento en su próxima compra:\n\n" +
                "Código de cupón: " + couponCode + "\n\n" +
                "Este cupón es válido por 30 días y solo puede ser utilizado una vez.\n\n" +
                "Si tiene alguna duda, por favor contáctenos.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        // Enviar el correo con el código de cupón
       sendMail(new EmailDTO(email, "\"Cupón de Bienvenida\"", plainTextMessage));
    }


}
