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
        String accountId = "66fe49c4f50c1b290ba159d8"; // ID de prueba
        String idEvent1 = "66fe4a3d6e8bef16ed873d70";
        String idEvent2 = "66fe4a4ef46d70182c90b56d";

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
        String idAccount = "66fdd93716b38c3e6e9d6259";
        String idEvent = "66fdd9c2665fe83cd883838c";
        cartService.removeItemFromCart(idAccount, idEvent);
    }

    @Test
    public void updateCartTest() throws Exception {
        String idAccount = "66fdd93716b38c3e6e9d6259";

    }

    @Test
    public void clearCartTest() throws Exception {
        String idAccount = "66fe49c4f50c1b290ba159d8";
        cartService.clearCart(idAccount);
    }

    @Test
    public void getCartItemsTest() throws Exception {
        String idAccount = "66fe49c4f50c1b290ba159d8";
        cartService.getCartItems(idAccount);
    }


}
