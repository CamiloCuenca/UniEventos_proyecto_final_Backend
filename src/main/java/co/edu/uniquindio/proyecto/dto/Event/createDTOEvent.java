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
        @NotBlank  EventStatus status,
        @NotBlank @Length(max = 1000) String description,
        @NotBlank String imageLocalities,
        @NotBlank  EventType type,
        @NotBlank  @Future() LocalDateTime date,@NotBlank
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s]+$")String city,
        @NotBlank String address,
        @NotBlank int amount,
        @NotBlank @Pattern(regexp = "^[a-zA-Z\\s]+$")
        @NotNull @Size(min = 1) List<Locality>localities
) {

}
