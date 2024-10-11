package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartItemSummaryDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.service.Implementation.CartServiceImp;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartServiceImp cartServiceImp;

    @Autowired
    private CartRepository cartRepository;

    /**
     * MEtodo para agregar items al carrito.
     *
     * @throws Exception
     */
    @Test
    public void addItemToCartTest() throws Exception {
        String accountId = "67087785418f0a4ecedaddfd"; // ID de prueba
        String idEvent1 = "66f5c5a0de22e82833106d92";
        String idEvent2 = "66f5c5a0de22e82833106d93";


        CartDetailDTO item1 = new CartDetailDTO("", idEvent1, Localities.VIP, 5);
        CartDetailDTO item2 = new CartDetailDTO("", idEvent2, Localities.VIP, 5);


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
        String accountId = "6706f47ba806a00dadbc61c6";
        String itemId = "6c40b96c-d12f-4f22-b72f-aa84541b9b89";
        UpdateCartItemDTO updateCartItemDTO = new UpdateCartItemDTO(10, Localities.VIP);
        cartService.updateCartItem(accountId, itemId, updateCartItemDTO);

    }


    /**
     * Metodo para Remover Item del carrito
     *
     * @throws Exception
     */
    @Test
    public void removeItemFromCart() throws Exception {
        String idAccount = "6706f47ba806a00dadbc61c6";
        String idEvent = "66f5c5a0de22e82833106d92";
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
        String idAccount = "6706f47ba806a00dadbc61c6";
        cartService.getCartItems(idAccount);
    }

    @Test
    public void validateCartBeforePaymentTest() throws Exception {
        String idAccount = "6706f47ba806a00dadbc61c6";
        cartService.validateCartBeforePayment(idAccount);
    }

    @Test
    public void getCartItemSummaryTest() throws Exception {
        String idAccount = "6706f47ba806a00dadbc61c6";
        cartService.getCartItemSummary(idAccount);
    }


}
