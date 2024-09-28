package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;

import java.util.Optional;

public interface CartService {

    void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception;

    void removeItemFromCart(String accountId, String eventId) throws Exception;

    void updateItemFromCart(String accountId,String eventId ) throws Exception;


}
