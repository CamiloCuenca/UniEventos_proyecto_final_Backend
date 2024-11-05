package co.edu.uniquindio.proyecto.dto.Account;

public record MessageDTOC<T>(
        boolean error,
        T respuesta, // Respuesta exitosa
        ErrorResponse errorResponse // Respuesta de error
) {
}
