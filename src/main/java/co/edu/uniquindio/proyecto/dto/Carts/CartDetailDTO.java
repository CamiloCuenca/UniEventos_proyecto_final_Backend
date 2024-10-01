package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CartDetailDTO(
        @NotBlank(message = "El ID del evento no puede estar vacío") String eventId,
        @NotBlank @Length(max = 100) String eventName,
        @NotBlank @Length(max = 100) String localityName,
        @NotBlank @Length(max = 100) String city,
        @Positive(message = "El precio debe ser positivo") double price,
        @Min(value = 1, message = "La cantidad debe ser al menos 1") int amount,
        @Min(value = 1, message = "La capacidad debe ser al menos 1") int capacity
) {
}
