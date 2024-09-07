package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.eventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionEventoDTO(
        String coverImage,
        String name,
        String description,
        String imageLocalities,
        eventType type,
        LocalDateTime date,
        String city,
        List<Locality> localities
) {
}
