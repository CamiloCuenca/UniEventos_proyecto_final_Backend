package co.edu.uniquindio.proyecto.dto.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;

import java.time.LocalDateTime;

public record CouponDTO(
        String name,
        String code,
        String discount,
        LocalDateTime expirationDate,
        CouponStatus status,
        TypeCoupon type
) { }
