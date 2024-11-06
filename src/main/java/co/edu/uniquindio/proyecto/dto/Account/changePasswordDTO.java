package co.edu.uniquindio.proyecto.dto.Account;

import co.edu.uniquindio.proyecto.model.Accounts.ValidationCodePassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record changePasswordDTO(
        @NotBlank(message = "La nueva contraseña no puede estar vacía")
        @Length(min = 7, max = 20, message = "La nueva contraseña debe tener entre 7 y 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[a-z]).{8,}$",
                message = "La contraseña debe tener al menos una letra mayúscula, un carácter especial (@#$%^&+=), un número y al menos 8 caracteres."
        )
        String newPassword,

        @NotBlank(message = "La contraseña de confirmación no puede estar vacía")
        @Length(min = 7, max = 20, message = "La contraseña de confirmación debe tener entre 7 y 20 caracteres")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*[@#$%^&+=])(?=.*[0-9])(?=.*[a-z]).{8,}$",
                message = "La contraseña debe tener al menos una letra mayúscula, un carácter especial (@#$%^&+=), un número y al menos 8 caracteres."
        )
        String confirmationPassword


) {
}
