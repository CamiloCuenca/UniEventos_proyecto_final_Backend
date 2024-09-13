package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartTotalDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImp implements CartService {
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) {

    }

    @Override
    public void removeItemFromCart(String accountId, String eventId) {

    }

    @Override
    public CartDTO getCartByAccountId(String accountId) {
        return null;
    }

    @Override
    public void updateItemQuantity(String accountId, String eventId, int quantity) {

    }

    @Override
    public void clearCart(String accountId) {

    }

    @Override
    public CartTotalDTO calculateTotal(String accountId) {
        return null;
    }
}
