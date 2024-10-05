package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartItemSummaryDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;

import java.util.List;

public interface CartService {

    void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception;

    void removeItemFromCart(String accountId,  String eventId) throws Exception;

    Cart createNewCart(String accountId)throws Exception;

    void clearCart(String accountId) throws Exception;

    List<CartDetail> getCartItems(String accountId) throws Exception;

    void updateCartItem (String accountId, String itemId, UpdateCartItemDTO UpdateCartItemDTO);

    void validateCartBeforePayment (String accountId) throws Exception;

    List<CartItemSummaryDTO> getCartItemSummary(String accountId) throws Exception;

}
