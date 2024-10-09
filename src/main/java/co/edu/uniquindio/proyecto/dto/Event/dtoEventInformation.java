package co.edu.uniquindio.proyecto.dto.Event;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDateTime;
import java.util.List;
public record dtoEventInformation(
        @NotBlank(message = "La imagen de portada no puede estar vacía") String coverImage,

        @NotBlank(message = "El nombre no puede estar vacío") String name,

        @NotBlank(message = "La descripción no puede estar vacía") String description,

        String imageLocalities,

        EventType type, // Considera hacer esto @NotNull si es necesario

        LocalDateTime date, // Deja esto como opcional si no es necesario

        @NotBlank(message = "La ciudad no puede estar vacía")
        @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "La ciudad solo puede contener letras y espacios") String city,

        @NotBlank(message = "La lista de localidades no puede estar vacía") List<Locality> localities
) {
}
