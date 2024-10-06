package co.edu.uniquindio.proyecto.dto.JWT;

public record MessageDTO<T>(
        boolean error,
        T respuesta
) {
}
