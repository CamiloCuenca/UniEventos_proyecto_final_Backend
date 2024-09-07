package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CrearCuentaDTO(
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 10) String telefono,
        @Length(max = 100) String direccion,
        @NotBlank @Length(max = 50) @Email String correo,
        @NotBlank @Length(min = 7, max = 20) String password
) {

}
