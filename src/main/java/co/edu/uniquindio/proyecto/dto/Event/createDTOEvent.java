package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.City;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record createDTOEvent(
        @NotBlank(message = "La imagen de portada no puede estar vacía") String coverImage,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Length(max = 100, message = "El nombre no puede exceder los 100 caracteres") String name,

        @NotNull(message = "El estado del evento no puede ser nulo") EventStatus status,

        @NotBlank(message = "La descripción no puede estar vacía")
        @Length(max = 1000, message = "La descripción no puede exceder los 1000 caracteres") String description,

        @NotBlank(message = "La imagen de las localidades no puede estar vacía") String imageLocalities,

        @NotNull(message = "El tipo de evento no puede ser nulo") EventType type,

        @NotNull(message = "La fecha del evento no puede ser nula")
        @Future(message = "La fecha debe ser en el futuro") LocalDateTime date,

        @NotBlank(message = "La ciudad no puede estar vacía")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "La ciudad solo puede contener letras y espacios") City city,

        @NotBlank(message = "La dirección no puede estar vacía") String address,

        @NotNull(message = "La cantidad no puede ser nula")
        @Positive(message = "La cantidad debe ser un número positivo") Integer amount,

        @NotNull(message = "La lista de localidades no puede ser nula")
        @Size(min = 1, message = "Debe haber al menos una localidad") List<Locality> localities
) { }
