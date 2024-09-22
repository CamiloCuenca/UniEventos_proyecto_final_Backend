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

import static org.simplejavamail.config.ConfigLoader.Property.SMTP_PORT;


@Service

public class EmailServiceImp implements EmailService {
    //private final String SMTP_USERNAME = "unieventosproyect@gmail.com";
    private final String SMTP_USERNAME = "camilocuencadev@gmail.com";
    //private final String  SMTP_PASSWORD = "vhopyvooxyyyrlne";
    private final String  SMTP_PASSWORD ="pvalybwlfpebggub";

    @Override
    @Async
    public void sendMail(EmailDTO emailDTO) throws Exception {
        Email email = EmailBuilder.startingBlank()
                .from(SMTP_USERNAME)
                .to(emailDTO.recipient())
                .withSubject(emailDTO.issue())
                .withPlainText(emailDTO.body())
                .buildEmail();


        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, SMTP_USERNAME, SMTP_PASSWORD)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {


            mailer.sendMail(email);
        }

    }
}
