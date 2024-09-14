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

        String accountId = "66a2a9aaa8620e3c1c5437be";
        CartDetailDTO  cartDetailDTO = new CartDetailDTO("66dcf9d99b293d0c2aba1374","Classical Concert","General","Medellín",80,2,250);

        cartService.addItemToCart(accountId,cartDetailDTO);

    }
}
