package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record EditarCuentaDTO(
        String id,
        @NotBlank @Length(max = 100) String username,
        @NotBlank @Length(max = 10) String phoneNumber,
        @Length(max = 100) String address,
        @NotBlank @Length(min = 7, max = 20) String password
) {
}
