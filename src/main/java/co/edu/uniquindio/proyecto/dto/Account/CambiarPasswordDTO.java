package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CambiarPasswordDTO(
        @NotBlank @Length(min = 7, max = 20) String codigoVerificacion,
        @NotBlank @Length(min = 7, max = 20) String passwordNueva
) {
}
