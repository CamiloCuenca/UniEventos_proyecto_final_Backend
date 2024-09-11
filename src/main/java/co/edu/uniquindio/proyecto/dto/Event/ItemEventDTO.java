package co.edu.uniquindio.proyecto.dto.Event;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record ItemEventDTO(
        @NotBlank String urlImagePoster,
        @NotBlank @Length(max = 100) String name,
        @NotBlank @Length(max = 20) LocalDateTime date,
        @Length(max = 100)String address
) {
}
