package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.model.Carts.CartDetail;

import java.util.List;

public record CartCartSummaryDTO(
        List<CartItemSummaryDTO> items,
        double total

) {
}
