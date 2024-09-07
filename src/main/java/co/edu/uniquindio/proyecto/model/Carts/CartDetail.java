package co.edu.uniquindio.proyecto.model.Carts;

import co.edu.uniquindio.proyecto.Enum.eventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartDetail {

    private int amount;
    private int capacity;
    private eventType type;
    private String address;
    private String city;
    private String site;
    private String localityName;
    private String eventName;
    private double price;
    private ObjectId idEvent;

}
