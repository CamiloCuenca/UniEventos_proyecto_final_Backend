package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.exception.EventNtFoundException;
import co.edu.uniquindio.proyecto.exception.account.InvalidadEmailException;
import co.edu.uniquindio.proyecto.exception.event.EventNotFoundException;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImp implements CartService {

    private final CartRepository cartRepository;
    private final EventRepository eventRepository;

    /**
     * This method add items to the account cart.
     *
     * @param accountId
     * @param cartDetailDTO
     * @throws Exception
     */
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws EventNotFoundException {
        // Buscar si el carrito ya existe para esta cuenta
        Optional<Cart> cartOptional = cartRepository.findByIdAccount(accountId);
        Cart cart;
        if (cartOptional.isPresent()) {
            cart = cartOptional.get();
        } else {
            cart = new Cart();
            cart.setAccountId(new ObjectId(accountId));
            cart.setDate(LocalDateTime.now());
            cart.setItems(new ArrayList<>());
        }

        // Buscar el evento por ID
        Optional<Event> optionalEvent = eventRepository.findById(cartDetailDTO.eventId());
        Event event = optionalEvent.orElseThrow(() -> new EventNotFoundException("No se encontró el evento"));

        // Comprobar si la localidad existe
        Locality locality = event.findByName(cartDetailDTO.localityName());
        if (locality == null) {
            throw new EventNotFoundException("No se encontró la localidad especificada");
        }

        // Usamos el Builder para crear el CartDetail a partir del DTO (record)
        CartDetail cartDetail = CartDetail.builder()
                .amount(cartDetailDTO.amount()) // cantidad de boletas que va comprar
                .capacity(cartDetailDTO.capacity()) // capacidad de personas
                .localityName(cartDetailDTO.localityName())
                .idEvent(event.getId())
                .build();

        // Añadimos el detalle del carrito a la lista de items
        cart.getItems().add(cartDetail);

        // Guardamos el carrito actualizado en MongoDB
        cartRepository.save(cart);
    }

    /**
     * This method remove item to the account cart.
     *
     * @param accountId
     * @param eventId
     * @throws Exception
     */
    @Override
    public void removeItemFromCart(String accountId, String eventId) throws CartNotFoundException, EventNotFoundException {
        // Obtener el carrito del repositorio
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No existe un carrito para el usuario con ID: " + accountId));

        // Verificar si el evento está presente en el carrito y eliminarlo
        boolean itemRemoved = cart.getItems().removeIf(item -> item.getIdEvent().equals(eventId));

        // Lanzar excepción si el item no fue encontrado
        if (!itemRemoved) {
            throw new EventNotFoundException("No se encontró el evento con ID: " + eventId + " en el carrito.");
        }

        // Guardar el carrito actualizado en MongoDB
        cartRepository.save(cart);
    }

    /**
     * this method return the account cart
     *
     * @param accountId
     * @return
     */
    @Override
    public void updateItemFromCart(String accountId, String eventId) throws Exception {

    }
}
