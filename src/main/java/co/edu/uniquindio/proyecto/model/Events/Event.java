package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.Enum.Localities;
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

    public Locality obtenerLocalidad(Localities nombreLocalidad) throws Exception {
        // Verificar que el nombre de la localidad no sea nulo
        if (nombreLocalidad == null) {
            throw new IllegalArgumentException("El nombre de la localidad no puede ser nulo.");
        }

        // Buscar la localidad en la lista de localidades del evento
        return localities.stream()
                .filter(locality -> locality.getName() == nombreLocalidad) // Comparación correcta
                .findFirst()
                .orElseThrow(() -> new Exception("No se encontró la localidad con el nombre: " + nombreLocalidad));
    }



}
