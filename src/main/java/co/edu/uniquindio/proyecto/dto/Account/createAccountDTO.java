package co.edu.uniquindio.proyecto.dto.Account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record createAccountDTO(
        @NotBlank @Length(max = 10) String idNumber,
        @NotBlank @Length(max = 100) String name,
        @NotBlank @Length(max = 10) String phoneNumber,
        @Length(max = 100) String address,
        @NotBlank @Length(max = 50) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String password

) {

}
