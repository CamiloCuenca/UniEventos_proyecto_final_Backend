package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCarDTO;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.exception.event.EventNotFoundException;
import co.edu.uniquindio.proyecto.exception.event.LocalityNotFoundException;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.CartRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void clearCart(String accountId) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));
        cart.setItems(new ArrayList<>());
        cart.calculateTotal();
        cartRepository.save(cart);
    }

    @Override
    public List<CartDetail> getCartItems(String accountId) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));

        // Obtener la lista de ítems del carrito
        List<CartDetail> cartItems = cart.getItems();

        // Imprimir los detalles de cada ítem usando toString()
        cartItems.forEach(item -> System.out.println(item.toString()));
        return cartItems;
    }

    @Override
    public void updateCartItem(String accountId, String eventId, String currentLocalityName, UpdateCarDTO updateCartDTO) {
        // Buscar el carrito del usuario
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));

        // Buscar el ítem en el carrito
        CartDetail item = cart.getItems().stream()
                .filter(detail -> detail.getIdEvent().equals(eventId) && detail.getLocalityName().equals(currentLocalityName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Ítem no encontrado en el carrito"));

        // Si se cambia la localidad, buscar el nuevo precio y verificar disponibilidad
        if (!currentLocalityName.equals(updateCartDTO.localityName())) {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

            Locality newLocality = event.getLocalities().stream()
                    .filter(loc -> loc.getName().equals(updateCartDTO.localityName()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Nueva localidad no encontrada"));

            item.setLocalityName(newLocality.getName()); // Actualiza la localidad
            item.setPrice(newLocality.getPrice()); // Actualiza el precio según la nueva localidad
        }

        // Actualizar la cantidad y calcular el nuevo subtotal
        item.setQuantity(updateCartDTO.quantity()); // Usar la cantidad del DTO
        item.setSubtotal(item.getPrice() * item.getQuantity());

        // Recalcular el total del carrito
        cart.calculateTotal();

        // Guardar el carrito actualizado en la base de datos
        cartRepository.save(cart);
    }

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
        Locality locality = event.getLocalities().stream()
                .filter(loc -> loc.getName().equals(cartDetailDTO.localityName()))
                .findFirst()
                .orElseThrow(() -> new LocalityNotFoundException("No se encontró la localidad: " + cartDetailDTO.localityName()));

        // Verificar si el ítem ya existe en el carrito
        boolean itemExists = cart.getItems().stream()
                .anyMatch(item -> item.getIdEvent().equals(event.getId()) && item.getLocalityName().equals(cartDetailDTO.localityName()));

        if (itemExists) {
            throw new IllegalArgumentException("El evento ya está en el carrito para la localidad especificada.");
        }

        // Crear el nuevo CartDetail y calcular el subtotal
        CartDetail newItem = CartDetail.builder()
                .idEvent(cartDetailDTO.eventId())
                .eventName(event.getName())
                .localityName(cartDetailDTO.localityName())
                .price(locality.getPrice())
                .quantity(cartDetailDTO.quantity()) // Asegúrate de establecer la cantidad aquí
                .subtotal(locality.getPrice() * cartDetailDTO.quantity()) // Calcular subtotal
                .build();

        // Agregar el ítem al carrito
        cart.getItems().add(newItem);

        // Recalcular el total del carrito
        cart.calculateTotal();

        // Guardar el carrito actualizado en la base de datos
        cartRepository.save(cart);
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
        cart.calculateTotal();
        cartRepository.save(cart);
    }
}
