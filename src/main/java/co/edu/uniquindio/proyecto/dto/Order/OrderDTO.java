package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import jakarta.validation.constraints.NotNull;


import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        @NotNull String idAccount,
        @NotNull LocalDateTime date,
        @NotNull String gatewayCode,
        @NotNull List<OrderDetail> items,
        @NotNull PaymentDTO payment,
        @NotNull Double total,
        String CodeCoupon
) {}
