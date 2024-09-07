package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.EventType;

public record FiltroEventoDTO(
        String nombre,
        EventType tipo,
        String ciudad
) {
}