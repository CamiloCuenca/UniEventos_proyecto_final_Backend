package co.edu.uniquindio.proyecto.dto.Eventos;

import co.edu.uniquindio.proyecto.Enum.eventType;

public record FiltroEventoDTO(
        String nombre,
        eventType tipo,
        String ciudad
) {
}