package co.edu.uniquindio.proyecto.dto.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record CouponDTO(
        @NotBlank @Length(max = 100) String name,
        @NotBlank @Length(min = 5, max = 5) String code,
        @NotBlank String discount,
        @NotNull LocalDateTime expirationDate,
        @NotNull CouponStatus status,
        @NotNull TypeCoupon type,
        // Nuevo campo para el evento específico
        String eventId,

        // Nuevos campos para el rango de fechas
        @NotNull LocalDateTime startDate
) {}
