package co.edu.uniquindio.proyecto.dto.JWT;

public record dtoMessage<T>(
        boolean error,
        T respuesta
) {
}
