package co.edu.uniquindio.proyecto.dto.Event;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


import java.time.LocalDateTime;
import java.util.List;
public record dtoEventInformation(
        @NotBlank String coverImage,
        @NotBlank String name,
        @NotBlank String description,
        String imageLocalities,
        EventType type,
        LocalDateTime date,
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s]+$") String city,
        @NotBlank List<Locality> localities
) {
}
