package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartItemSummaryDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.exception.Cart.*;
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
import java.util.stream.Collectors;

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
     * @param accountId     ID de la cuenta del usuario
     * @param cartDetailDTO DTO destinado para la creacion de los detailDTO
     * @throws Exception
     */
    @Override
    public void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) {
        // Buscar o crear el carrito
        Cart cart = getOrCreateCart(accountId);

        // Buscar el evento
        Event event = findEventById(cartDetailDTO.eventId());

        // Verificar y obtener la localidad
        Locality locality = findLocalityInEvent(event, (cartDetailDTO.localityName()));

        // Verificar si el ítem ya existe en el carrito
        checkIfItemExistsInCart(cart, event, String.valueOf(cartDetailDTO.localityName()));

        // Crear y agregar el nuevo ítem al carrito
        CartDetail newItem = createCartItem(cartDetailDTO, event, locality);
        addItemToCart(cart, newItem);

        // Recalcular el total y guardar el carrito
        recalculateCartTotalAndSave(cart);
    }

    // Bloque de métodos usados para Agregar Items al carrito.
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
        // Busca un evento por su ID en el repositorio
        return eventRepository.findById(eventId)
                // Si no se encuentra el evento, lanza una excepción personalizada
                .orElseThrow(() -> new EventNotFoundException("No se encontró el evento con el ID: " + eventId));
    }

    private Locality findLocalityInEvent(Event event, Localities localityName) throws LocalityNotFoundException {
        // Filtra las localidades del evento para encontrar la que coincide con el nombre proporcionado
        return event.getLocalities().stream()
                // Filtra las localidades que tienen el mismo nombre que 'localityName'
                .filter(loc -> loc.getName().equals(localityName))
                // Intenta obtener la primera localidad que coincida
                .findFirst()
                // Si no se encuentra ninguna, lanza una excepción personalizada
                .orElseThrow(() -> new LocalityNotFoundException("No se encontró la localidad: " + localityName));
    }

    private void checkIfItemExistsInCart(Cart cart, Event event, String localityName) {
        // Validar parámetros
        if (cart == null || event == null || localityName == null || localityName.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o vacíos.");
        }

        // Verifica si ya existe un ítem en el carrito para el evento y la localidad especificados
        boolean itemExists = cart.getItems().stream()
                // Comprueba si algún ítem en el carrito coincide con el ID del evento y el nombre de la localidad
                .anyMatch(item -> item.getEventId().equals(event.getId()) && item.getLocalityName().equals(localityName));

        // Si se encuentra un ítem que coincide, lanza una excepción personalizada
        if (itemExists) {
            throw new EventoAlreadyForLocalityException("El evento ya está en el carrito para la localidad especificada.");
        }
    }

    private CartDetail createCartItem(CartDetailDTO cartDetailDTO, Event event, Locality locality) {
        // Crea un nuevo objeto CartDetail utilizando el patrón Builder.
        return CartDetail.builder()
                .itemId(UUID.randomUUID().toString()) // Asigna un ID único al ítem utilizando UUID
                .eventId(cartDetailDTO.eventId()) // Establece el ID del evento a partir del DTO
                .eventName(event.getName()) // Establece el nombre del evento desde el objeto Event
                .localityName(Localities.valueOf(String.valueOf(cartDetailDTO.localityName()))) // Convierte el nombre de la localidad desde el DTO al tipo Localities
                .price(locality.getPrice()) // Establece el precio de la localidad
                .quantity(cartDetailDTO.quantity()) // Establece la cantidad especificada en el DTO
                .subtotal(locality.getPrice() * cartDetailDTO.quantity()) // Calcula el subtotal multiplicando el precio por la cantidad
                .build(); // Construye y devuelve el objeto CartDetail
    }

    private void addItemToCart(Cart cart, CartDetail newItem) {
        // Agrega el nuevo ítem al carrito utilizando la lista de ítems del carrito
        cart.getItems().add(newItem); // Se añade el ítem proporcionado al carrito
    }

    private void recalculateCartTotalAndSave(Cart cart) {
        // Recalcula el total del carrito, actualizando su estado interno
        cart.calculateTotal(); // Llama al método calculateTotal para que el carrito recalculé el total

        // Guarda el carrito actualizado en el repositorio
        cartRepository.save(cart); // Persiste el carrito en la base de datos
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
        if (!item.getLocalityName().equals(updateCartItemDTO.localityName())) {
            // Buscar el evento para obtener las localidades disponibles
            Event event = eventRepository.findById(item.getEventId())
                    .orElseThrow(() -> new EventNotFoundException("Evento no encontrado con ID: " + item.getEventId()));

            // Buscar la nueva localidad
            Locality newLocality = event.getLocalities().stream()
                    .filter(loc -> loc.getName().equals(updateCartItemDTO.localityName()))
                    .findFirst()
                    .orElseThrow(() -> new LocalityNotFoundException("Nueva localidad no encontrada: " + updateCartItemDTO.localityName()));

            // Actualizar localidad y precio
            item.setLocalityName(newLocality.getName()); // Asigna la nueva localidad
            item.setPrice(newLocality.getPrice());       // Asigna el nuevo precio según la localidad
        }

        // Actualizar la cantidad y el subtotal
        item.setQuantity(updateCartItemDTO.quantity()); // Establece la nueva cantidad del ítem
        item.setSubtotal(item.getPrice() * item.getQuantity()); // Calcula y actualiza el subtotal
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
        // Buscar el carrito asociado a la cuenta utilizando el ID de la cuenta
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));

        // Limpiar los ítems del carrito, estableciendo una nueva lista vacía
        cart.setItems(new ArrayList<>());

        // Calcular el total del carrito después de limpiar los ítems
        cart.calculateTotal();

        // Guardar el carrito actualizado en el repositorio
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
        // Buscar el carrito asociado a la cuenta utilizando el ID de la cuenta
        Cart cart = cartRepository.findByIdAccount(accountId)
                .orElseThrow(() -> new CartNotFoundException("No se encontró el carrito para la cuenta: " + accountId));

        // Obtener la lista de ítems del carrito
        List<CartDetail> cartItems = cart.getItems();

        // Imprimir los detalles de cada ítem usando toString()
        cartItems.forEach(item -> System.out.println(item.toString()));

        // Devolver la lista de ítems del carrito
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

    @Override
    public void validateCartBeforePayment(String accountId) throws insufficientAvailabilityException {
        // Buscar el carrito del usuario
        Cart cart = getCartByAccountId(accountId);

        // Verificar la disponibilidad de cada ítem en el carrito
        for (CartDetail item : cart.getItems()) {
            Event event = findEventById(item.getEventId());
            Locality locality = findLocalityInEvent(event, item.getLocalityName());

            // Comprobar la disponibilidad de boletos
            if (locality.getMaximumCapacity() - locality.getTicketsSold() < item.getQuantity()) {
                throw new insufficientAvailabilityException("No hay suficientes boletos disponibles para el evento: " + item.getEventName());
            }
        }
        // Si llegamos aquí, el carrito es válido
        System.out.println("El carrito es válido y listo para el pago.");

    }

    @Override
    public List<CartItemSummaryDTO> getCartItemSummary(String accountId) throws CartNotFoundException {
        // Buscar el carrito del usuario
        Cart cart = getCartByAccountId(accountId);

        // Obtener el resumen de los ítems en el carrito
        List<CartItemSummaryDTO> summary = cart.getItems().stream()
                .map(item -> new CartItemSummaryDTO(
                        item.getEventName(),
                        item.getLocalityName(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        printCartSummary(summary); // Cambia para pasar el resumen directamente
        return summary;
    }

    /**
     * Método para imprimir el resumen del carrito en la consola.
     *
     * @param summary Resumen del carrito.
     */
    public void printCartSummary(List<CartItemSummaryDTO> summary) {
        // Imprimir encabezado
        System.out.println("Resumen del Carrito:");
        System.out.printf("%-30s %-20s %-10s %-10s %-10s%n", "Evento", "Localidad", "Cantidad", "Precio", "Subtotal");
        System.out.println("--------------------------------------------------------------");

        // Imprimir cada ítem en el carrito
        for (CartItemSummaryDTO item : summary) {
            System.out.printf("%-30s %-20s %-10d %-10.2f %-10.2f%n",
                    item.eventName(),
                    item.localities(),
                    item.quantity(),
                    item.price(),
                    item.subtotal());
        }

        // Imprimir total del carrito
        double total = summary.stream()
                .mapToDouble(CartItemSummaryDTO::subtotal)
                .sum();
        System.out.printf("Total del carrito: %.2f%n", total);
    }
}
