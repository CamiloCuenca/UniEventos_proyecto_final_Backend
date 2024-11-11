package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record dtoAccountItem(
        @NotBlank(message = "El ID no puede estar vacío.")
        @Length(max = 10, message = "El ID no puede exceder los 10 caracteres.")
        String id,

        @NotBlank(message = "El nombre no puede estar vacío.")
        @Length(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
        String name,

        @NotBlank(message = "El número de teléfono no puede estar vacío.")
        @Length(max = 10, message = "El número de teléfono no puede exceder los 10 caracteres.")
        String phoneNumber,

        @NotBlank(message = "El email electrónico no puede estar vacío.")
        @Length(max = 50, message = "El email electrónico no puede exceder los 50 caracteres.")
        @Email(message = "El formato del email electrónico es inválido.")
        String email

) {}
