package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CartDetailDTO(
        String id,
        @NotBlank(message = "El ID del evento no puede estar vacío") String eventId,
        @NotNull(message = "El nombre de la localidad no puede ser nulo") Localities localityName,
        @Positive(message = "La cantidad debe ser positiva") @Min(value = 1, message = "La cantidad debe ser al menos 1") int quantity
) {
}
