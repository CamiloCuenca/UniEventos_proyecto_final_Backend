package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.exception.event.EventNotFoundException;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImp implements CartService {

    // Repositorios para interactuar con la base de datos
    private final CartRepository cartRepository;
    private final EventRepository eventRepository;

    /**
     * Crea un nuevo carrito para un usuario dado su ID de cuenta.
     *
     * @param accountId ID de la cuenta del usuario.
     * @return El carrito creado.
     * @throws Exception En caso de error al crear el carrito.
     */
    @Override
    public Cart createNewCart(String accountId) throws Exception {
        Cart newCart = new Cart();
        newCart.setId(accountId); // Asigna el ID de la cuenta al carrito
        newCart.setDate(LocalDateTime.now()); // Establece la fecha de creación del carrito
        newCart.setItems(new ArrayList<>()); // Inicializa la lista de ítems del carrito
        return cartRepository.save(newCart); // Guarda el carrito en el repositorio
    }

    /**
     * Agrega un ítem al carrito de un usuario dado su ID de cuenta.
     *
     * @param accountId     ID de la cuenta del usuario.
     * @param cartDetailDTO Datos del ítem a agregar.
     * @throws Exception En caso de error al agregar el ítem.
     */
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception {
        // Buscar o crear el carrito
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseGet(() -> {
                    try {
                        return createNewCart(accountId); // Crea un nuevo carrito si no existe
                    } catch (Exception e) {
                        throw new RuntimeException("Error al crear el carrito: " + e.getMessage());
                    }
                });

        // Buscar el evento por ID
        Event event = eventRepository.findById(cartDetailDTO.eventId())
                .orElseThrow(() -> new EventNotFoundException("No se encontró el evento con el ID: " + cartDetailDTO.eventId()));

        // Verificar la localidad
        Locality locality = event.findByName(cartDetailDTO.localityName());
        if (locality == null) {
            throw new EventNotFoundException("No se encontró la localidad: " + cartDetailDTO.localityName());
        }

        // Verificar si el ítem ya existe en el carrito
        boolean itemExists = cart.getItems().stream()
                .anyMatch(item -> item.getIdEvent().equals(event.getId()) && item.getLocalityName().equals(cartDetailDTO.localityName()));

        if (itemExists) {
            throw new IllegalArgumentException("El evento ya está en el carrito para la localidad especificada.");
        }

        // Agregar el ítem al carrito
        addItemToCart(cart, cartDetailDTO, event);
    }

    /**
     * Método auxiliar para agregar el ítem al carrito.
     *
     * @param cart          El carrito donde se va a agregar el ítem.
     * @param cartDetailDTO Datos del ítem a agregar.
     * @param event         El evento asociado al ítem.
     */
    private void addItemToCart(Cart cart, CartDetailDTO cartDetailDTO, Event event) {
        // Crear un nuevo detalle del carrito
        CartDetail cartDetail = CartDetail.builder()
                .amount(cartDetailDTO.amount()) // Establecer la cantidad del ítem
                .capacity(cartDetailDTO.capacity()) // Establecer la capacidad del ítem
                .localityName(cartDetailDTO.localityName()) // Establecer el nombre de la localidad
                .idEvent(event.getId()) // Establecer el ID del evento
                .build();

        cart.getItems().add(cartDetail); // Agregar el ítem a la lista de ítems
        cartRepository.save(cart); // Guardar el carrito actualizado
    }

    /**
     * Elimina un ítem del carrito de un usuario dado su ID de cuenta y el ID del evento.
     *
     * @param accountId ID de la cuenta del usuario.
     * @param eventId   ID del evento a eliminar.
     * @throws Exception En caso de error al eliminar el ítem.
     */
    @Override
    public void removeItemFromCart(String accountId, String eventId) throws Exception {
        // Buscar el carrito para la cuenta
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));

        // Verificar y eliminar el ítem del carrito
        boolean itemRemoved = cart.getItems().removeIf(item -> item.getIdEvent().equals(eventId));
        if (!itemRemoved) {
            throw new EventNotFoundException("No se encontró el evento con ID: " + eventId + " en el carrito.");
        }

        // Guardar los cambios en el carrito
        cartRepository.save(cart);
    }
}
