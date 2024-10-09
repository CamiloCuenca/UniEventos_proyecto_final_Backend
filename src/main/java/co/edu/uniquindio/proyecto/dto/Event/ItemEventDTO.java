package co.edu.uniquindio.proyecto.dto.Event;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

public record ItemEventDTO(
        @NotBlank(message = "La URL de la imagen del cartel no puede estar vacía")
        String urlImagePoster,

        @NotBlank(message = "El nombre no puede estar vacío")
        @Length(max = 100, message = "El nombre no debe exceder los 100 caracteres")
        String name,

        @NotNull(message = "La fecha no puede ser nula")
        @Future(message = "La fecha debe ser en el futuro") // Si `date` es LocalDateTime
        LocalDateTime date,

        @Length(max = 100, message = "La dirección no debe exceder los 100 caracteres")
        String address
) {
}
