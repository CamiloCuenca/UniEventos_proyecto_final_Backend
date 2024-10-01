package co.edu.uniquindio.proyecto.model.Carts;

import co.edu.uniquindio.proyecto.Enum.EventType;
import lombok.*;
import org.bson.types.ObjectId;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartDetail {
    private int amount;
    private int capacity;
    private String localityName;
    private String idEvent;


}
