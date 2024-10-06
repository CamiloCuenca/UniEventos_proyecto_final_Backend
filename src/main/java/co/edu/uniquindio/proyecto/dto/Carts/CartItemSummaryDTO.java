package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;

public record CartItemSummaryDTO(
        String eventName,
        Localities localities,
        int quantity,
        double price,
        double subtotal
) {

}
