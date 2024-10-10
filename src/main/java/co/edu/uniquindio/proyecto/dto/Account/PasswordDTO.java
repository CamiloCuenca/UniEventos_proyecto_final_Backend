package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;

public record PasswordDTO(
        @NotBlank(message = "La contraseña no puede estar vacía")
         String password
) {

}

