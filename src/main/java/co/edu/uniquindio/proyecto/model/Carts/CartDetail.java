package co.edu.uniquindio.proyecto.model.Carts;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class CartDetail {
    private String idEvent;
    private String eventName;
    private String localityName;
    private double price;
    private int quantity;
    private double subtotal;

}
