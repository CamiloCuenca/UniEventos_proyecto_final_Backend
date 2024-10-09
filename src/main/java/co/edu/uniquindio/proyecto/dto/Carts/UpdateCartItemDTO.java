package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record UpdateCartItemDTO(
        @Positive(message = "La cantidad debe ser un valor positivo")
        @Min(value = 1, message = "La cantidad mínima es 1") int quantity,

        @NotNull(message = "El nombre de la localidad no puede ser nulo") Localities localityName
) {


}
