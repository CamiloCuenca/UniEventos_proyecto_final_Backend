package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;


public record dtoEventFilter(
        @NotBlank(message = "El nombre no puede estar vacío")
        @Length(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9\\s\\-']+$", message = "El nombre solo puede contener letras, números, espacios y guiones")
        String name,

        @NotNull(message = "El tipo de evento no puede ser nulo")
        EventType type,

        @NotBlank(message = "La ciudad no puede estar vacía")
        @Pattern(regexp = "^[a-zA-Z\\s\\-']+$", message = "La ciudad solo puede contener letras, espacios y guiones")
        String city,

        @FutureOrPresent(message = "La fecha no puede ser en el pasado")
        LocalDateTime date


        // O puedes considerar agregar startDate y endDate para rango de búsqueda
) {
}