package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record createAccountDTO(
        @NotBlank(message = "El número de identificación no puede estar vacío.")
        @Length(max = 10, message = "El número de identificación no puede exceder los 10 caracteres.")
        String idNumber,

        @NotBlank(message = "El nombre no puede estar vacío.")
        @Length(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name,

        @NotBlank(message = "El número de teléfono no puede estar vacío.")
        @Length(max = 10, message = "El número de teléfono no puede exceder los 10 caracteres.")
        String phoneNumber,

        @Length(max = 100, message = "La dirección no puede exceder los 100 caracteres.")
        String address,

        @NotBlank(message = "El email electrónico no puede estar vacío.")
        @Length(max = 50, message = "El email electrónico no puede exceder los 50 caracteres.")
        @Email(message = "El formato del email electrónico es inválido.")
        String email,

        @NotBlank(message = "La contraseña no puede estar vacía.")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[a-z]).{8,}$",
                message = "La contraseña debe tener al menos una letra mayúscula, un carácter especial (@#$%^&+=), un número y al menos 8 caracteres."
        )
        String password

) {

}
