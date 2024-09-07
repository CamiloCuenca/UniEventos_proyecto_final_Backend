package co.edu.uniquindio.proyecto.dto.Event;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ItemEventoDTO(
        @NotBlank String urlImagenPoster,
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 20)String fecha,
        @Length(max = 100)String direccion
) {
}
