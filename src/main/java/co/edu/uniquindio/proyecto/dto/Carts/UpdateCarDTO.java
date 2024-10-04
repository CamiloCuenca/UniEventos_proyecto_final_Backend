package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateCarDTO(
        @Positive int quantity, // Cambiado a quantity
        @NotBlank @Length(max = 100) String localityName
) {


}
