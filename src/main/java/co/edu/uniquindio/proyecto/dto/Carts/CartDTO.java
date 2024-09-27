package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        @NotBlank String id,
        LocalDateTime date,
        List<CartDetailDTO> items,
        @Positive double totalEventPrice

) {
}
