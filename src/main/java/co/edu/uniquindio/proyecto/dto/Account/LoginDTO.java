package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginDTO(
        @NotNull(message = "El email electrónico no puede estar vacío.")
        @Email(message = "El email electrónico debe tener un formato válido.")
        String email,

        @NotNull(message = "La contraseña no puede estar vacía.")
        @NotBlank(message = "La contraseña no puede estar vacía.")
        String password
) {
}
