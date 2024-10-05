package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.Localities;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Locality {

    private Double price;
    private Localities name;
    private int ticketsSold;
    private int maximumCapacity;
}
