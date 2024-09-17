package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetail {


    private int id;
    private ObjectId idEvent;
    private double price;
    private String localityName;
    private String EventName;
    private int amount;

}
