package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record dtoAccountInformation(

        @NotBlank(message = "El nombre no puede estar vacío.")
        @Length(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name,

        @NotBlank(message = "El número de teléfono no puede estar vacío.")
        @Length(max = 10, message = "El número de teléfono no puede exceder los 10 caracteres.")
        String phoneNumber,

        @Length(max = 100, message = "La dirección no puede exceder los 100 caracteres.")
        String address

) {
}

