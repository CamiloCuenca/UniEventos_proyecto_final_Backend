package co.edu.uniquindio.proyecto.model.Coupons;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document("Coupon")
public class Coupon {

    @Id
    private String couponId;
    private LocalDateTime expirationDate;
    private String code;
    private CouponStatus status;
    private TypeCoupon type;
    private String name;
    private String discount;

    // Nuevo campo para asociar el cupón a un evento específico
    private String eventId;

    // Nuevos campos para manejar el rango de fechas
    private LocalDateTime startDate;


    // Método auxiliar para generar un código de cupón aleatorio
    public String generateRandomCouponCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();  // Código aleatorio de 8 caracteres
    }
}

