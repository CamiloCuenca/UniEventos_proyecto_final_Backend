package co.edu.uniquindio.proyecto.dto.Carts;


public record CartDetailDTO(
        String eventId,
        String eventName,
        String localityName,
        String city,
        double price,
        int amount
) {
}
