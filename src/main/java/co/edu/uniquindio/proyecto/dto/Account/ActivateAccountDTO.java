package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ActivateAccountDTO {
    @NotBlank
    private String correo;

    @NotBlank
    private String code;
}