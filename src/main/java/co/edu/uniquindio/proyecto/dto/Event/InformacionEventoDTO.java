package co.edu.uniquindio.proyecto.dto.Event;


import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;


import java.time.LocalDateTime;
import java.util.List;

public record InformacionEventoDTO(
        String coverImage,
        String name,
        String description,
        String imageLocalities,
        EventType type,
        LocalDateTime date,
        String city,
        List<Locality> localities
) {
}
