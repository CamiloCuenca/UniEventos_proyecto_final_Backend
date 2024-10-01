package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    @Test
    public void addItemToCartTest() throws Exception {
        String accountId = "66fc426f64eff50b3f24ef3d"; // ID de prueba
        String idEvent1 = "66fc42f82ec16c3574caaf16";
        String idEvent2 = "66fc4859e26def327e823367";

        // Crear los DTOs de los ítems
        CartDetailDTO item1 = new CartDetailDTO(idEvent1, "Evento 1", "General", "Ciudad A", 100.0, 2, 100);
        CartDetailDTO item2 = new CartDetailDTO(idEvent2, "Evento 1", "VIP", "Ciudad A", 100.0, 2, 100);

        // Agregar ítems al carrito
        cartService.addItemToCart(accountId, item1);
        cartService.addItemToCart(accountId, item2);

        // Obtener el carrito desde la base de datos
        Cart cart = cartRepository.findByIdAccount(accountId).orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Verificar que el carrito tiene dos ítems
        assertEquals(2, cart.getItems().size());
        assertTrue(cart.getItems().stream().anyMatch(item -> item.getIdEvent().equals(idEvent1)));
        assertTrue(cart.getItems().stream().anyMatch(item -> item.getIdEvent().equals(idEvent2)));
    }

    @Test
    public void removeItemFromCart() throws Exception {
        String idAccount = "66fc426f64eff50b3f24ef3d";
        String idEvent = "66fc42f82ec16c3574caaf16";
        cartService.removeItemFromCart(idAccount, idEvent);
    }


}
