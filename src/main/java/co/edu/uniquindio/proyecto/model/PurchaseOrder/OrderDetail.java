package co.edu.uniquindio.proyecto.model.PurchaseOrder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@Setter
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
