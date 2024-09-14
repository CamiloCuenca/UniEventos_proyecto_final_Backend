package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Carts.CartDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartTotalDTO;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;

public interface CartService {

    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception;

    void removeItemFromCart(String accountId, String eventId) throws Exception;

    CartDTO getCartByAccountId(String accountId);

    void updateItemQuantity(String accountId, String eventId, int quantity);

    void clearCart(String accountId);

    CartTotalDTO calculateTotal(String accountId);

}
