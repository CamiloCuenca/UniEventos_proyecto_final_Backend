package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        @NotBlank(message = "El ID no puede estar vacío") String id,
        @NotNull(message = "La fecha no puede ser nula") LocalDateTime date,
        @NotNull(message = "La lista de artículos no puede ser nula") List<CartDetailDTO> items

) {
}
