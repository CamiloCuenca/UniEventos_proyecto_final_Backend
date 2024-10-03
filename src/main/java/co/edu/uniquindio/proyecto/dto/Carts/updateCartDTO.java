package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record updateCartDTO(
        @Positive int amount,
        @NotBlank @Length(max = 100) String localityName
) {


}
