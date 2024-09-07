package co.edu.uniquindio.proyecto.dto.Account;

public record CambiarPasswordDTO(
        String codigoVerificacion,
        String passwordNueva
) {
}
