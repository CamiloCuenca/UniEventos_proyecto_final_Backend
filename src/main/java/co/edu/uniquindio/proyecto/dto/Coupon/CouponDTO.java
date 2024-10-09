package co.edu.uniquindio.proyecto.dto.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CouponDTO(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres") String name,

        @NotBlank(message = "El código no puede estar vacío")
        @Size(min = 10, max = 20, message = "El código debe tener exactamente 5 caracteres") String code,

        @NotBlank(message = "El descuento no puede estar vacío") String discount,

        @NotNull(message = "La fecha de expiración no puede ser nula") LocalDateTime expirationDate,

        @NotNull(message = "El estado no puede ser nulo") CouponStatus status,

        @NotNull(message = "El tipo de cupón no puede ser nulo") TypeCoupon type,

        // Campo opcional para el evento específico
        String eventId,

        @NotNull(message = "La fecha de inicio no puede ser nula") LocalDateTime startDate
) {}
