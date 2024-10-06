package co.edu.uniquindio.proyecto.dto.Event;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

public record createDTOEvent(
        @NotBlank String coverImage,
        @NotBlank @Length(max = 100) String name,
        @NotNull EventStatus status, // Cambiado a @NotNull
        @NotBlank @Length(max = 1000) String description,
        @NotBlank String imageLocalities,
        @NotNull EventType type, // Cambiado a @NotNull
        @NotNull @Future LocalDateTime date,
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s]+$") String city,
        @NotBlank String address,
        @NotNull @Min(0) Integer amount, // Cambiado a Integer
        @NotNull @Size(min = 1) List<Locality> localities
) { }
