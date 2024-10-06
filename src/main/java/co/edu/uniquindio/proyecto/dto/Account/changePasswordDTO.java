package co.edu.uniquindio.proyecto.dto.Account;

import co.edu.uniquindio.proyecto.model.Accounts.ValidationCodePassword;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record changePasswordDTO(
        @NotBlank @Length(min = 7, max = 20) String newPassword,
        @NotBlank @Length(min = 7, max = 20) String confirmationPassword,
        String code
) {
}
