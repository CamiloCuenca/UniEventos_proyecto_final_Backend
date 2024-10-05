package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepository cartRepository;

    /**
     * MEtodo para agregar items al carrito.
     *
     * @throws Exception
     */
    @Test
    public void addItemToCartTest() throws Exception {
        String accountId = "66f8db70c1ce3939dbcbe1e0"; // ID de prueba
        String idEvent1 = "66f5c5a0de22e82833106d92";
        String idEvent2 = "66f5c5a0de22e82833106d93";

        CartDetailDTO item1 = new CartDetailDTO("", idEvent1, Localities.GENERAL, 5); // Verifica que la cantidad sea 5
        CartDetailDTO item2 = new CartDetailDTO("", idEvent2, Localities.VIP, 5); // Verifica que la cantidad sea 5


        // Agregar ítems al carrito
        cartService.addItemToCart(accountId, item1);
        cartService.addItemToCart(accountId, item2);

    }

    /**
     * MEtodo para actualizar un item del carrito.
     *
     * @throws Exception
     */
    @Test
    public void upgradeItemToCartTest() throws Exception {
        String accountId = "66f8db70c1ce3939dbcbe1e0";
        String itemId = "c524ca22-aded-4584-8f68-bd5f014051bf";
        UpdateCartItemDTO updateCartItemDTO = new UpdateCartItemDTO(5, Localities.VIP);
        cartService.updateCartItem(accountId, itemId, updateCartItemDTO);

    }


    /**
     * Metodo para Remover Item del carrito
     *
     * @throws Exception
     */
    @Test
    public void removeItemFromCart() throws Exception {
        String idAccount = "67002c50a09f9570a45ef428";
        String idEvent = "66fe4a4ef46d70182c90b56d";
        cartService.removeItemFromCart(idAccount, idEvent);
    }


    /**
     * Metodo para limpiar el carrito.
     *
     * @throws Exception
     */
    @Test
    public void clearCartTest() throws Exception {
        String idAccount = "66f8db70c1ce3939dbcbe1e0";
        cartService.clearCart(idAccount);
    }

    /**
     * Metodo para listar el carrito.
     *
     * @throws Exception
     */
    @Test
    public void getCartItemsTest() throws Exception {
        String idAccount = "66fe49c4f50c1b290ba159d8";
        cartService.getCartItems(idAccount);
    }


}
