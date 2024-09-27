package co.edu.uniquindio.proyecto.dto.Carts;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

public record CartDetailDTO(
        String eventId,
        @NotBlank @Length(max = 100) String eventName,
        @NotBlank @Length(max = 100) String localityName,
        @NotBlank @Length(max = 100) String city,
        @Positive double price,
        @Min(1) int amount,
        @Min(1) int capacity
) {
}
