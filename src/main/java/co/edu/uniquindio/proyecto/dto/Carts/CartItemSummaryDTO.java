package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CartItemSummaryDTO(
        @NotBlank(message = "El nombre del evento no puede estar vacío") String eventName,
        Localities localities,

        @Positive(message = "La cantidad debe ser un valor positivo")
        @Min(value = 1, message = "La cantidad mínima es 1") int quantity,

        @Positive(message = "El precio debe ser un valor positivo") double price,

        @Positive(message = "El subtotal debe ser un valor positivo") double subtotal
) {

}
