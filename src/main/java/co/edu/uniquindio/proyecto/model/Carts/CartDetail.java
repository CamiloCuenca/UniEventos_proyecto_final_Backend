package co.edu.uniquindio.proyecto.model.Carts;

import co.edu.uniquindio.proyecto.Enum.Localities;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartDetail {
    private String eventId;
    private String itemId; // ID único para el ítem del carrito
    private String eventName;
    private Localities localityName;
    private Double price;
    private int quantity;
    private Double subtotal;


}
