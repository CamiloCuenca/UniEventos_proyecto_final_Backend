package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.eventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Document("Evento")
public class Event {
    @Id
    private String id;

    private String coverImage;
    private String name;
    private EventStatus status;
    private String description;
    private String direction;
    private String imageLocalities;
    private eventType type;
    private LocalDateTime date;
    private String city;
    private String site;
    private List<Locality> localities;
    private int capacity;
    private double price;

}
