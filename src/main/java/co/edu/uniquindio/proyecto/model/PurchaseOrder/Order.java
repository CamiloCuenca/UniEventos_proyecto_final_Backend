package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document ("Order")
public class Order {

    @Id
    private String id;
    private ObjectId idAccount;
    private LocalDateTime date;
    private String gatewayCode;
    private List<OrderDetail> items;
    private Payment payment;
    private Double total;
    private String codeCoupon;
}
