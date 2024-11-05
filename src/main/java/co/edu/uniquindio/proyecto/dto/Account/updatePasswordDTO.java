package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;

public record updatePasswordDTO(
        @NotBlank(message = "La contraseña no puede estar vacía")
        String currentPassword,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String newPassword,
        @NotBlank(message = "La contraseña no puede estar vacía")
        String confirmationPassword
) {
}
