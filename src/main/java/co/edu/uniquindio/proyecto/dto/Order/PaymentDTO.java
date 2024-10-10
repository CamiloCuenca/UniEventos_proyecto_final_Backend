package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.Enum.PaymentType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Pago;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record PaymentDTO(
        @NotBlank(message = "La moneda no puede estar vacía") String currency,
        @NotNull(message = "El tipo de pago no puede ser nulo") PaymentType typePayment,
        @NotBlank(message = "El código de autorización no puede estar vacío") String authorizationCode,
        @NotNull(message = "La fecha no puede ser nula") LocalDateTime date,
        @NotNull(message = "El valor de la transacción no puede ser nulo")
        @Positive(message = "El valor de la transacción debe ser positivo") double transactionValue,
        @NotNull(message = "El estado del pago no puede ser nulo") PaymentState state
) {
    public Pago toEntity() {
        return Pago.builder()
                .currency(this.currency)
                .typePayment(this.typePayment)
                .authorizationCode(this.authorizationCode)
                .date(this.date)
                .transactionValue(this.transactionValue)
                .state(this.state)
                .build();
    }
}
