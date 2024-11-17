package co.edu.uniquindio.proyecto.dto.Order;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;


import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        @NotNull(message = "El ID de la cuenta no puede ser nulo")
        String idAccount,

        @NotNull(message = "La fecha no puede ser nula")
        LocalDateTime date,

        @NotNull(message = "La lista de artículos no puede ser nula")
        List<OrderDetail> items,


        @Length(max = 20, message = "El código del cupón no debe exceder los 20 caracteres")
        String codeCoupon
) {}
