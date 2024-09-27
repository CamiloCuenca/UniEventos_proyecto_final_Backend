package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record dtoAccountItem(
        @NotBlank @Length(max = 10) String id,
        @NotBlank @Length(max = 100) String name,
        @NotBlank @Length(max = 10) String phoneNumber,
        @NotBlank @Length(max = 50) @Email String email

) {}
