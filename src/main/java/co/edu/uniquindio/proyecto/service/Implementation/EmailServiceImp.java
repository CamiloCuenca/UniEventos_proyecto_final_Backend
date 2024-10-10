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

    /**  Envía un correo electrónico.
     *
     * @param emailDTO Data Transfer Object que contiene la información del correo electrónico a enviar.
     * @throws Exception
     */
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

    /** Envía un código QR por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el QR.
     * @param qrUrl La URL de la imagen del código QR.
     */
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

    /** Envía un código de validación por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de validación.
     * @param validationCode El código de validación a enviar.
     * @throws Exception
     */
    @Async
    @Override
    public void sendCodevalidation(String email, String validationCode) throws Exception {


        String htmlMessage = "<html><body>" +
                "<p>Estimado usuario,</p>" +
                "<p>Gracias por registrarse en nuestra plataforma. Para activar su cuenta, por favor utilice el siguiente código de activación:</p>" +
                "<h3>Código de activación: " + validationCode + "</h3>" +
                "<p>Este código es válido por 15 minutos.</p>" +
                "<p>Si usted no solicitó este registro, por favor ignore este correo.</p>" +
                "<p>Atentamente,<br/>El equipo de UniEventos</p>" +
                "</body></html>";



        sendMail(new EmailDTO(email, "\"Activación de cuenta\"", htmlMessage));

    }


    /** Envía un código de recuperación de contraseña por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de recuperación.
     * @param passwordValidationCode El código de validación de la contraseña a enviar.
     * @throws Exception
     */
    @Override
    @Async
    public void sendRecoveryCode(String email, String passwordValidationCode) throws Exception {
        // Crear el mensaje a enviar por correo
        String htmlMessage = "<html><body>" +
                "<p>Estimado usuario,</p>" +
                "<p>Ha solicitado recuperar su contraseña. Utilice el siguiente código de recuperación para restablecer su contraseña:</p>" +
                "<h3>Código de recuperación: " + passwordValidationCode + "</h3>" +
                "<p>Este código es válido por 15 minutos.</p>" +
                "<p>Si usted no solicitó esta recuperación, por favor ignore este correo.</p>" +
                "<p>Atentamente,<br/>El equipo de UniEventos</p>" +
                "</body></html>";


        // Enviar el correo electrónico con el código de recuperación
        sendMail(new EmailDTO(email, "Recuperación de contraseña", htmlMessage));
    }

    /** Envía un cupón de bienvenida por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el cupón.
     * @param couponCode El código del cupón de bienvenida a enviar.
     * @throws Exception
     */
    @Async
    @Override
    public void sendWelcomeCoupon(String email, String couponCode) throws Exception {
        // Preparar el cuerpo del correo que incluye el código del cupón
        String htmlMessage = "<html><body>" +
                "<p>Estimado usuario,</p>" +
                "<p>Gracias por registrarse en nuestra plataforma. Para celebrar su registro, le ofrecemos un cupón de bienvenida con un 15% de descuento en su próxima compra:</p>" +
                "<h3>Código de cupón: " + couponCode + "</h3>" +
                "<p>Este cupón es válido por 30 días y solo puede ser utilizado una vez.</p>" +
                "<p>Si tiene alguna duda, por favor contáctenos.</p>" +
                "<p>Atentamente,<br/>El equipo de UniEventos</p>" +
                "</body></html>";


        // Enviar el correo con el código de cupón
       sendMail(new EmailDTO(email, "\"Cupón de Bienvenida\"", htmlMessage));
    }


}
