package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.Enum.PaymentType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Pago;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PaymentDTO(
        @NotNull String currency,
        @NotNull PaymentType typePayment,   // Usar el Enum PaymentType
        @NotNull String authorizationCode,
        @NotNull LocalDateTime date,
        @NotNull double transactionValue,
        @NotNull PaymentState state         // Usar el Enum PaymentState
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
