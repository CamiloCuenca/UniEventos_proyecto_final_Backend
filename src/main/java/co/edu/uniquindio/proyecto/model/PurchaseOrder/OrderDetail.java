package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import co.edu.uniquindio.proyecto.Enum.Localities;
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
    private Localities localityName;
    private String EventName;
    private int amount;

}
