package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateCartItemDTO(
        @Positive int quantity, // Cambiado a quantity
        @NotBlank @Length(max = 100) Localities localityName
) {


}
