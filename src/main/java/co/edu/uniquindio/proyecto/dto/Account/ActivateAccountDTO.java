package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActivateAccountDTO(
        @NotBlank(message = "El código no puede estar vacío")
        String code,
        @NotBlank(message = "El email no puede estar vacío")
        @Email(message = "Debe seguir la estructura del corre")
        String email


) {

}