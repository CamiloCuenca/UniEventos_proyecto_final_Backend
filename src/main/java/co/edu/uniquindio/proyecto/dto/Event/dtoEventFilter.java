package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


public record dtoEventFilter(
        @NotBlank @Length(max = 100) String name,
        @NotNull EventType type,
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s\\-']+$") String city,
        LocalDateTime date // Deja esto como opcional si no es necesario
) {
}