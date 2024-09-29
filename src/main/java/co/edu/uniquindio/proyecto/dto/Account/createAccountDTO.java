package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record createAccountDTO(
        @NotBlank @Length(max = 10) String idNumber,
        @NotBlank @Length(max = 100) String name,
        @NotBlank @Length(max = 10) String phoneNumber,
        @Length(max = 100) String address,
        @NotBlank @Length(max = 50) @Email String email,

        @NotBlank(message = "La contraseña no puede estar vacía.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[a-z]).{8,}$",
                message = "La contraseña debe tener al menos una letra mayúscula, un carácter especial (@#$%^&+=), un número y al menos 8 caracteres."
        )
        String password

) {

}
