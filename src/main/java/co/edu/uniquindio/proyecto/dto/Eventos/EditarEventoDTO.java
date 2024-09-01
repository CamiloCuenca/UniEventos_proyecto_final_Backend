package co.edu.uniquindio.proyecto.dto.Eventos;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.locality;

import java.time.LocalDateTime;
import java.util.List;

public record EditarEventoDTO(
        String coverImage,
        String name,
        EventStatus status,
        String description,
        String imageLocalities,
        EventType type,
        LocalDateTime date,
        String city,
        List<locality> localities
) {
}
