package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.EmailDTO;

public interface EmailService {
    void sendMail(EmailDTO emailDTO) throws Exception;
    void sendQrByEmail(String email, String qrImageUrl) throws Exception;
}
