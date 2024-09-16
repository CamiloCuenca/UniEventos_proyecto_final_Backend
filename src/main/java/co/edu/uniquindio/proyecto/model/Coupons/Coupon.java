package co.edu.uniquindio.proyecto.model.Coupons;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Document("Cupon")
public class Coupon {

    @Id
    private String id;
    private LocalDateTime expirationDate;
    private String code;
    private CouponStatus status;
    private TypeCoupon type;
    private String name;
    private String discount;



}

