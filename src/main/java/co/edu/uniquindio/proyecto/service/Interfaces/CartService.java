package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartItemSummaryDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.model.Carts.Cart;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;

import java.util.List;

public interface CartService {

    /**
     * Agrega un nuevo ítem al carrito de compras del usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @param cartDetailDTO El objeto CartDetailDTO que contiene la información del ítem a agregar.
     * @throws Exception Si ocurre un error al agregar el ítem al carrito.
     */
    void addItemToCart(String accountId, CartDetailDTO cartDetailDTO) throws Exception;

    /**
     * Elimina un ítem específico del carrito de compras del usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @param eventId El ID del evento a eliminar del carrito.
     * @throws Exception Si ocurre un error al eliminar el ítem del carrito.
     */
    void removeItemFromCart(String accountId, String eventId) throws Exception;

    /**
     * Crea un nuevo carrito de compras para el usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @return Un objeto Cart que representa el nuevo carrito creado.
     * @throws Exception Si ocurre un error durante la creación del carrito.
     */
    Cart createNewCart(String accountId) throws Exception;

    /**
     * Limpia todos los ítems del carrito de compras del usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @throws Exception Si ocurre un error al limpiar el carrito.
     */
    void clearCart(String accountId) throws Exception;

    /**
     * Obtiene todos los ítems del carrito de compras del usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @return Una lista de objetos CartDetail que representan los ítems en el carrito.
     * @throws Exception Si ocurre un error al obtener los ítems del carrito.
     */
    List<CartDetail> getCartItems(String accountId) throws Exception;

    /**
     * Actualiza la información de un ítem en el carrito de compras.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @param itemId El ID del ítem a actualizar.
     * @param updateCartItemDTO El objeto UpdateCartItemDTO que contiene la nueva información del ítem.
     */
    void updateCartItem(String accountId, String itemId, UpdateCartItemDTO updateCartItemDTO);

    /**
     * Valida el carrito de compras antes de proceder al pago.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @throws Exception Si el carrito no cumple con las condiciones necesarias para realizar el pago.
     */
    void validateCartBeforePayment(String accountId) throws Exception;

    /**
     * Obtiene un resumen de los ítems en el carrito de compras del usuario.
     *
     * @param accountId El ID de la cuenta del usuario.
     * @return Una lista de objetos CartItemSummaryDTO que resumen la información de los ítems en el carrito.
     * @throws Exception Si ocurre un error al obtener el resumen de los ítems.
     */
    List<CartItemSummaryDTO> getCartItemSummary(String accountId) throws Exception;

}
