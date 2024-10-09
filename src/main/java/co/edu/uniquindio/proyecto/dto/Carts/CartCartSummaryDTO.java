package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public record CartCartSummaryDTO(
        @NotNull(message = "La lista de ítems no puede ser nula")
        @NotEmpty(message = "El carrito debe contener al menos un ítem")
        List<CartItemSummaryDTO> items,

        @NotNull(message = "El total no puede ser nulo")
        BigDecimal total

) {
}
