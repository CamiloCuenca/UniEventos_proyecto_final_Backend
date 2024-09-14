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
@Document("Eventos")
public class Event {
    @Id
    private String id;
    private String coverImage;
    private String name;
    private EventStatus status;
    private String description;
    private String imageLocalities;
    private EventType type;
    private LocalDateTime date;
    private String city;
    private String address;
    private List<Locality> localities;

    public Locality findByName(String name) {
        return localities.stream().filter(locality -> locality.getName().equals(name)).findFirst().orElse(null);
    }

}
