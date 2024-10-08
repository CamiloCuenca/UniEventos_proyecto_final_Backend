package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface EmailService {

    /**
     * Envía un correo electrónico.
     *
     * @param emailDTO Data Transfer Object que contiene la información del correo electrónico a enviar.
     * @throws Exception Si ocurre un error al enviar el correo electrónico.
     */
    void sendMail(EmailDTO emailDTO) throws Exception;

    /**
     * Envía un código QR por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el QR.
     * @param qrImageUrl La URL de la imagen del código QR.
     * @throws Exception Si ocurre un error al enviar el QR por correo electrónico.
     */
    void sendQrByEmail(String email, String qrImageUrl) throws Exception;

    /**
     * Envía un código de validación por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de validación.
     * @param validationCode El código de validación a enviar.
     * @throws Exception Si ocurre un error al enviar el código de validación.
     */
    void sendCodevalidation(String email, String validationCode) throws Exception;

    /**
     * Envía un código de recuperación por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el código de recuperación.
     * @param passwordValidationCode El código de validación de la contraseña a enviar.
     * @throws Exception Si ocurre un error al enviar el código de recuperación.
     */
    void sendRecoveryCode(String email, String passwordValidationCode) throws Exception;

    /**
     * Envía un cupón de bienvenida por correo electrónico.
     *
     * @param email La dirección de correo electrónico a la que se enviará el cupón.
     * @param couponCode El código del cupón de bienvenida a enviar.
     * @throws Exception Si ocurre un error al enviar el cupón de bienvenida.
     */
    void sendWelcomeCoupon(String email, String couponCode) throws Exception;

}
