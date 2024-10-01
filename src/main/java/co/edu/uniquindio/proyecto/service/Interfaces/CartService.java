package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.exception.event.EventNotFoundException;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import org.bson.types.ObjectId;

import java.util.Optional;

public interface CartService {

    void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception;

    void removeItemFromCart(String accountId,  String eventId) throws Exception;

    Cart createNewCart(String accountId)throws Exception;





}
