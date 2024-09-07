package co.edu.uniquindio.proyecto.model.Events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Locality {

    private Double price;
    private String name;
    private int ticketsSold;
    private int maximumCapacity;
}
