package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Document ("Orden")
public class Order {

    @Id
    private String id;
    private ObjectId idUser;
    private LocalDateTime date;
    private String gatewayCode;
    private List<OrderDetail> items;
    private Payment payment;
    private Double total;
    private ObjectId idCoupon;
}
