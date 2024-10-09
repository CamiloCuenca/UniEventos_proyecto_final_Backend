package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActivateAccountDTO {
    @NotBlank(message = "El correo no puede estar vacío")
    @Email(message = "Debe seguir la estructura del corre")
    private String correo;

    @NotBlank(message = "El código no puede estar vacío")
    private String code;
}