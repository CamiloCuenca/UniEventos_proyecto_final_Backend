package co.edu.uniquindio.proyecto.dto.Carts;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO(
        String id,
        LocalDateTime date,
        List<CartDetailDTO> items,
        double totalEventPrice

) {
}
