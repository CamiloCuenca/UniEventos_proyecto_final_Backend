package co.edu.uniquindio.proyecto.dto.Cuenta;

public record CambiarPasswordDTO(
        String codigoVerificacion,
        String passwordNueva
) {
}
