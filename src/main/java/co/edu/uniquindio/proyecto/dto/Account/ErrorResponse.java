package co.edu.uniquindio.proyecto.dto.Account;

public record ErrorResponse(
        String message,
        int status // Código de estado HTTP
) {
}
