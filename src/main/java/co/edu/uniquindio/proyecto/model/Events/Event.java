package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document("Event")
public class Event {
    @Id
    private String id;
    private String coverImage;
    private String name;
    private EventStatus status; // ACTIVE, INACTIVE
    private String description;
    private String imageLocalities;
    private EventType type;
    private LocalDateTime date;
    private String city;
    private String address;
    private int amount;
    private List<Locality> localities;



}
