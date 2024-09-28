package co.edu.uniquindio.proyecto.model.Cart;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartTest {

    @Autowired
    private CartService cartService;

    @Test
    public void addItemToCartTest() throws Exception {

        String accountId = "66f79539c15bdf6a1c74cb2d";
        CartDetailDTO  cartDetailDTO = new CartDetailDTO("66f5c5a0de22e82833106d92","Food Festival 2024","General","Medellín",30,2,250);

        cartService.addItemToCart(accountId,cartDetailDTO);

    }

    @Test
    public void removeItemFromCartTest() throws Exception {
        String accountId = "66f79539c15bdf6a1c74cb2d";
        String eventId = "66f5c5a0de22e82833106d92";

        cartService.removeItemFromCart(accountId,eventId);

    }
}
