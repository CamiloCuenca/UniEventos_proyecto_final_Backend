package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.exception.Cart.ErrorCreateForCartException;
import co.edu.uniquindio.proyecto.exception.Cart.EventoAlreadyForLocalityException;
import co.edu.uniquindio.proyecto.exception.Cart.NoItemFoundException;
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
import java.util.UUID;

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
     * Metodo encargado de agregar item al carro, en caso de que el usuario no tenga uno lo crea autimaticamente con los items
     *
     * @param accountId
     * @param cartDetailDTO
     * @throws Exception
     */
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception {
        // Buscar o crear el carrito
        Cart cart = getOrCreateCart(accountId);

        // Buscar el evento
        Event event = findEventById(cartDetailDTO.eventId());

        // Verificar y obtener la localidad
        Locality locality = findLocalityInEvent(event, cartDetailDTO.localityName());

        // Verificar si el ítem ya existe en el carrito
        checkIfItemExistsInCart(cart, event, cartDetailDTO.localityName());

        // Crear y agregar el nuevo ítem al carrito
        CartDetail newItem = createCartItem(cartDetailDTO, event, locality);
        addItemToCart(cart, newItem);

        // Recalcular el total y guardar el carrito
        recalculateCartTotalAndSave(cart);
    }

    // Bloque de metodos usados para Agregar Items al carrito.
    private Cart getOrCreateCart(String accountId) throws ErrorCreateForCartException {
        return cartRepository.findByIdAccount(accountId)
                .orElseGet(() -> {
                    try {
                        return createNewCart(accountId);
                    } catch (Exception e) {
                        throw new ErrorCreateForCartException("Error al crear el carrito: " + e.getMessage());
                    }
                });
    }

    private Event findEventById(String eventId) throws EventNotFoundException {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("No se encontró el evento con el ID: " + eventId));
    }

    private Locality findLocalityInEvent(Event event, String localityName) throws LocalityNotFoundException {
        return event.getLocalities().stream()
                .filter(loc -> loc.getName().equals(localityName))
                .findFirst()
                .orElseThrow(() -> new LocalityNotFoundException("No se encontró la localidad: " + localityName));
    }

    private void checkIfItemExistsInCart(Cart cart, Event event, String localityName) {
        boolean itemExists = cart.getItems().stream()
                .anyMatch(item -> item.getEventId().equals(event.getId()) && item.getLocalityName().equals(localityName));
        if (itemExists) {
            throw new EventoAlreadyForLocalityException("El evento ya está en el carrito para la localidad especificada.");
        }
    }

    private CartDetail createCartItem(CartDetailDTO cartDetailDTO, Event event, Locality locality) {
        return CartDetail.builder()
                .itemId(UUID.randomUUID().toString()) // Asigna un ID único al ítem
                .eventId(cartDetailDTO.eventId())
                .eventName(event.getName())
                .localityName(cartDetailDTO.localityName())
                .price(locality.getPrice())
                .quantity(cartDetailDTO.quantity()) // Establecer la cantidad
                .subtotal(locality.getPrice() * cartDetailDTO.quantity()) // Calcular subtotal
                .build();
    }

    private void addItemToCart(Cart cart, CartDetail newItem) {
        cart.getItems().add(newItem);
    }

    private void recalculateCartTotalAndSave(Cart cart) {
        cart.calculateTotal();
        cartRepository.save(cart);
    }

    // Fin del bloque.


    /**
     * Metodo para actualizar los items del carrito.
     *
     * @param accountId
     * @param itemId
     * @param UpdateCartItemDTO
     */
    @Override
    public void updateCartItem(String accountId, String itemId, UpdateCartItemDTO UpdateCartItemDTO) {
        // Buscar el carrito del usuario
        Cart cart = getCartByAccountId(accountId);

        // Buscar el ítem en el carrito usando el ID único del ítem
        CartDetail item = findCartItemById(cart, itemId);

        // Actualizar el ítem con los nuevos datos
        updateCartItemDetails(item, UpdateCartItemDTO);

        // Recalcular el total del carrito
        recalculateCartTotalAndSave(cart);

    }

    //Bloque de metodos usados para implementar el metodo de actualizar los items.
    private CartDetail findCartItemById(Cart cart, String itemId) throws NoItemFoundException {
        return cart.getItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new NoItemFoundException("Ítem no encontrado en el carrito con ID: " + itemId));
    }

    private Cart getCartByAccountId(String accountId) throws CartNotFoundException {
        return cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("Carrito no encontrado para la cuenta: " + accountId));
    }

    private void updateCartItemDetails(CartDetail item, UpdateCartItemDTO updateCartItemDTO) throws EventNotFoundException, LocalityNotFoundException {
        // Verificar si la localidad ha cambiado
        if (!item.getLocalityName().equalsIgnoreCase(updateCartItemDTO.localityName())) {
            // Buscar el evento para obtener las localidades disponibles
            Event event = eventRepository.findById(item.getEventId())
                    .orElseThrow(() -> new EventNotFoundException("Evento no encontrado con ID: " + item.getEventId()));

            // Buscar la nueva localidad
            Locality newLocality = event.getLocalities().stream()
                    .filter(loc -> loc.getName().equals(updateCartItemDTO.localityName()))
                    .findFirst()
                    .orElseThrow(() -> new LocalityNotFoundException("Nueva localidad no encontrada: " + updateCartItemDTO.localityName()));

            // Actualizar localidad y precio
            item.setLocalityName(newLocality.getName());
            item.setPrice(newLocality.getPrice());
        }

        // Actualizar la cantidad y el subtotal
        item.setQuantity(updateCartItemDTO.quantity());
        item.setSubtotal(item.getPrice() * item.getQuantity());
    }
    //Fin del bloque

    /**
     * Metodo para limpiar los items del carrito.
     *
     * @param accountId
     * @throws CartNotFoundException
     */
    @Override
    public void clearCart(String accountId) throws CartNotFoundException {
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));
        cart.setItems(new ArrayList<>());
        cart.calculateTotal();
        cartRepository.save(cart);
    }

    /**
     * Metodo para listar los items del carrito.
     *
     * @param accountId
     * @return
     * @throws CartNotFoundException
     */
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

    /**
     * Metodo para removerItems del carrito.
     *
     * @param accountId
     * @param eventId
     * @throws CartNotFoundException
     * @throws EventNotFoundException
     */
    @Override
    public void removeItemFromCart(String accountId, String eventId) throws CartNotFoundException, EventNotFoundException {
        // Buscar el carrito para la cuenta
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));

        // Verificar y eliminar el ítem del carrito
        boolean itemRemoved = cart.getItems().removeIf(item -> item.getEventId().equals(eventId));
        if (!itemRemoved) {
            throw new EventNotFoundException("No se encontró el evento con ID: " + eventId + " en el carrito.");
        }

        // Guardar los cambios en el carrito
        cart.calculateTotal();
        cartRepository.save(cart);
    }
}
