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
                "<p>Si no puede ver el código QR, haga clic en el siguiente enlace para visualizarlo:</p>" +
                "<a href=\"" + qrUrl + "\">Ver Código QR</a>" +
                "<p>Atentamente,<br/>El equipo de UniEventos</p>" +
                "</body></html>";

        try {
            sendMail(new EmailDTO(email, "Código QR de su Orden", htmlMessage));
        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones, podrías usar un logger aquí
        }
    }
}
