package co.edu.uniquindio.proyecto.dto.Eventos;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.locality;

import java.time.LocalDateTime;
import java.util.List;

public record InformacionEventoDTO(
        String coverImage,
        String name,
        String description,
        String imageLocalities,
        EventType type,
        EventStatus status,
        LocalDateTime date,
        String city,
        List<locality> localities


) {
}
