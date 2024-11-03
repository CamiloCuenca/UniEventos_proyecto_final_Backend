package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;

public record updatePassword(
        @NotBlank(message = "La contraseña no puede estar vacía")
        String currentPassword,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String newPassword
) {
}
