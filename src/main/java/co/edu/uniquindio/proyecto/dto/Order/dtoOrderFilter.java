package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.Enum.PaymentState;

public record dtoOrderFilter(
        PaymentState state
) {
}
