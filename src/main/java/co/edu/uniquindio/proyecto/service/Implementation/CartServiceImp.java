package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final EventService eventService;

    /** This method add items to the account cart.
     *
     * @param accountId
     * @param cartDetailDTO
     * @throws Exception
     */
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception {
        // Buscar si el carrito ya existe para esta cuenta

        Optional<Cart> cartOptional = getCartByAccountId(accountId);
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            cart = new Cart();
            cart.setIdAccount(new ObjectId(accountId));
            cart.setDate(LocalDateTime.now());
            cart.setItems(new ArrayList<>());
        }
        Event event = eventService.getById( cartDetailDTO.eventId() );
        Locality locality = event.findByName( cartDetailDTO.localityName() );
        // Usamos el Builder para crear el CartDetail a partir del DTO (record)
        CartDetail cartDetail = CartDetail.builder()
                .amount(cartDetailDTO.amount())//cantidad de boletas que va comprar
                .capacity(cartDetailDTO.capacity())//capacidad de personas.
                .localityName(cartDetailDTO.localityName())
                .idEvent(event.getId())
                .build();
        // Añadimos el detalle del carrito a la lista de items
        cart.getItems().add(cartDetail);
        // Guardamos el carrito actualizado en MongoDB
        cartRepository.save(cart);
    }

    /** This method remove item to the account cart.
     *
     * @param accountId
     * @param eventId
     * @throws Exception
     */
    @Override
    public void removeItemFromCart(String accountId, String eventId) throws Exception {
        Optional<Cart> cartOptional = cartRepository.findByIdAccount(new ObjectId(accountId));

        if(cartOptional.isEmpty()) {
            throw new Exception("No existe un carrito para el usuario");
        }

        Cart cart = cartOptional.get();
        List<CartDetail> items = cart.getItems();

        for(CartDetail item : items) {
            if(item.getIdEvent().equals(eventId)) {
                cart.getItems().remove(item);
                break;
            }
        }
        cartRepository.save(cart);

    }

    /** this method return the account cart
     *
     * @param accountId
     * @return
     */
    @Override
    public Optional<Cart> getCartByAccountId(String accountId) {
        return cartRepository.findByIdAccount(new ObjectId(accountId));
    }


    @Override
    public void updateItemFromCart(String accountId, String eventId) throws Exception {

    }
}
