package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Test
    public void addItemToCartTest() throws Exception {

        String accountId = "66a2c1517f3b340441ffdeb0";
        CartDetailDTO  cartDetailDTO = new CartDetailDTO("66dcf9d99b293d0c2aba1376","Food Festival 2024","General","Medellín",30,2,250);

        cartService.addItemToCart(accountId,cartDetailDTO);

    }

    @Test
    public void removeItemFromCartTest() throws Exception {
        String accountId = "66a2c1517f3b340441ffdeb0";
        String eventId = "66dcf9d99b293d0c2aba1376";

        cartService.removeItemFromCart(accountId,eventId);

    }

}
