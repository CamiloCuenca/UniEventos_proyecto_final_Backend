package co.edu.uniquindio.proyecto.model.Carts;

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
    private String localityName;
    private Double price;
    private int quantity;
    private Double subtotal;


}
