package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.JWT.dtoMessage;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/email")
public class EmailController {

    private final EmailService emailService;

    // Enviar correo electrónico genérico
    @PostMapping("/send-email")
    public ResponseEntity<dtoMessage<String>> sendMail(@Valid @RequestBody EmailDTO emailDTO) {
        try {
            emailService.sendMail(emailDTO);
            return ResponseEntity.ok(new dtoMessage<>(false, "Correo enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new dtoMessage<>(true, "Error al enviar el correo: " + e.getMessage()));
        }
    }

    // Enviar código QR por correo electrónico
    @PostMapping("/send-qr")
    public ResponseEntity<dtoMessage<String>> sendQrByEmail(@RequestParam String email, @RequestParam String qrImageUrl) {
        try {
            emailService.sendQrByEmail(email, qrImageUrl);
            return ResponseEntity.ok(new dtoMessage<>(false, "Correo con QR enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new dtoMessage<>(true, "Error al enviar el correo con QR: " + e.getMessage()));
        }
    }

    // Enviar código de validación por correo electrónico
    @PostMapping("/send-validation-code")
    public ResponseEntity<dtoMessage<String>> sendCodeValidation(@RequestParam String email, @RequestParam String validationCode) {
        try {
            emailService.sendCodevalidation(email, validationCode);
            return ResponseEntity.ok(new dtoMessage<>(false, "Correo con código de validación enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new dtoMessage<>(true, "Error al enviar el correo con código de validación: " + e.getMessage()));
        }
    }

    // Enviar código de recuperación de contraseña
    @PostMapping("/send-recovery-code")
    public ResponseEntity<dtoMessage<String>> sendRecoveryCode(@RequestParam String email, @RequestParam String recoveryCode) {
        try {
            emailService.sendRecoveryCode(email, recoveryCode);
            return ResponseEntity.ok(new dtoMessage<>(false, "Correo con código de recuperación enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new dtoMessage<>(true, "Error al enviar el correo con código de recuperación: " + e.getMessage()));
        }
    }

    // Enviar cupón de bienvenida por correo electrónico
    @PostMapping("/send-welcome-coupon")
    public ResponseEntity<dtoMessage<String>> sendWelcomeCoupon(@RequestParam String email, @RequestParam String couponCode) {
        try {
            emailService.sendWelcomeCoupon(email, couponCode);
            return ResponseEntity.ok(new dtoMessage<>(false, "Correo con cupón de bienvenida enviado exitosamente."));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new dtoMessage<>(true, "Error al enviar el correo con cupón de bienvenida: " + e.getMessage()));
        }
    }
}
