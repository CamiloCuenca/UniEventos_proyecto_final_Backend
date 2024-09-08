package co.edu.uniquindio.proyecto.model.Events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Locality {

    private Double price;
    private String name;
    private int ticketsSold;
    private int maximumCapacity;
}
