package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCarDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
        String accountId = "67002c50a09f9570a45ef428"; // ID de prueba
        String idEvent1 = "66fe4a3d6e8bef16ed873d70";
        String idEvent2 = "66fe4a4ef46d70182c90b56d";

        // Crear los DTOs de los ítems
        CartDetailDTO item1 = new CartDetailDTO(idEvent1, "VIP", 5); // Verifica que la cantidad sea 5
        CartDetailDTO item2 = new CartDetailDTO(idEvent2, "General", 2); // Verifica que la cantidad sea 2

        // Agregar ítems al carrito
        cartService.addItemToCart(accountId, item1);
        cartService.addItemToCart(accountId, item2);


    }

    @Test
    public void upgradeItemToCartTest() throws Exception {
        String accountId = "67002c50a09f9570a45ef428"; // ID de prueba
        String eventId = "66fe4a3d6e8bef16ed873d70"; // ID de evento a actualizar
        String currentLocalityName = "VIP"; // Localidad actual
        UpdateCarDTO updateCarDTO = new UpdateCarDTO(
                1, "General" // Nueva localidad a la que se desea cambiar
        );

        // Llamar al método que actualiza el ítem en el carrito
        assertDoesNotThrow(() -> {
            cartService.updateCartItem(accountId, eventId, currentLocalityName, updateCarDTO);
        });

        // Obtener el carrito actualizado desde la base de datos
        Cart updatedCart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Verificar que el ítem se actualizó correctamente
        CartDetail updatedItem = updatedCart.getItems().stream()
                .filter(detail -> detail.getIdEvent().equals(eventId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado en el carrito"));

        // Verificar que la localidad se haya actualizado
        assertEquals("General", updatedItem.getLocalityName());
        // Verificar que la cantidad se haya actualizado
        assertEquals(1, updatedItem.getQuantity());
        // Verificar que el precio también se haya actualizado según la nueva localidad

    }


    @Test
    public void removeItemFromCart() throws Exception {
        String idAccount = "67002c50a09f9570a45ef428";
        String idEvent = "66fe4a4ef46d70182c90b56d";
        cartService.removeItemFromCart(idAccount, idEvent);
    }

    @Test
    public void updateCartTest() throws Exception {
        String idAccount = "66fdd93716b38c3e6e9d6259";

    }

    @Test
    public void clearCartTest() throws Exception {
        String idAccount = "67002c50a09f9570a45ef428";
        cartService.clearCart(idAccount);
    }

    @Test
    public void getCartItemsTest() throws Exception {
        String idAccount = "66fe49c4f50c1b290ba159d8";
        cartService.getCartItems(idAccount);
    }

    @Test
    public void calculateTotalTest() throws Exception {
    }


}
