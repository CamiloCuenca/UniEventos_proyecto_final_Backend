package co.edu.uniquindio.proyecto.dto.Carts;

import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import lombok.*;
import org.bson.types.ObjectId;


public record CartDetailDTO(
        String eventId,
        String eventName,
        String localityName,
        String city,
        double price,
        int amount,
        int capacity
) {
}
