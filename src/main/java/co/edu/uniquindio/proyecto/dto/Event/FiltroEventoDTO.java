package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record FiltroEventoDTO(
        @NotBlank @Length(max = 100) String nombre,
        EventType tipo,
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s]+$")String ciudad
) {
}