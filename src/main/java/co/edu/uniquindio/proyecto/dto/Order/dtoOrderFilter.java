package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.Enum.PaymentState;
import jakarta.validation.constraints.NotNull;

public record dtoOrderFilter(
        @NotNull(message = "El estado de pago no puede ser nulo") PaymentState state
) {
}
