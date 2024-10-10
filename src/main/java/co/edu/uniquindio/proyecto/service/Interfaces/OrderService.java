package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Account.LoginDTO;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import com.mercadopago.resources.preference.Preference;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * Crear una nueva orden.
     *
     * @param orderDTO Objeto OrderDTO que contiene los detalles de la nueva orden.
     * @return Objeto Order que representa la orden creada.
     * @throws Exception Si ocurre un error durante la creación de la orden.
     */
    Order createOrder(OrderDTO orderDTO) throws Exception;

    /**
     * Actualizar una orden existente.
     *
     * @param orderId ID de la orden que se desea actualizar.
     * @param updatedOrderDTO Objeto OrderDTO que contiene los datos actualizados de la orden.
     * @return Objeto Order con la información actualizada.
     * @throws Exception Si ocurre un error durante la actualización de la orden.
     */
    Order updateOrder(String orderId, OrderDTO updatedOrderDTO) throws Exception;

    /**
     * Eliminar una orden.
     *
     * @param orderId ID de la orden que se desea eliminar.
     * @throws Exception Si ocurre un error durante la eliminación de la orden.
     */
    void deleteOrder(String orderId) throws Exception;

    /**
     * Listar todas las órdenes de una cuenta específica.
     *
     * @param accountId ID de la cuenta del usuario.
     * @return Lista de órdenes asociadas a la cuenta del usuario.
     * @throws Exception Si ocurre un error al obtener las órdenes.
     */
    List<Order> getOrdersByUser(String accountId) throws Exception;

    /**
     * Listar todas las órdenes.
     *
     * @return Lista de todas las órdenes.
     * @throws Exception Si ocurre un error al obtener las órdenes.
     */
    List<Order> getAllOrders() throws Exception;

    /**
     * Realizar el pago de una orden mediante MercadoPago.
     *
     * @param idOrden ID de la orden para la cual se realiza el pago.
     * @return Objeto Preference que contiene los detalles de la preferencia de pago creada.
     * @throws Exception Si ocurre un error durante el proceso de pago.
     */
    Preference realizarPago(String idOrden) throws Exception;

    /**
     * Recibir y manejar la notificación de MercadoPago.
     *
     * @param request Mapa que contiene la información de la notificación recibida.
     */
    void recibirNotificacionMercadoPago(Map<String, Object> request);

    /**
     * Obtener una orden por su ID.
     *
     * @param idOrden ID de la orden que se desea obtener.
     * @return Objeto Order que representa la orden solicitada.
     * @throws Exception Si no se encuentra la orden con el ID proporcionado.
     */
    Order obtenerOrden(String idOrden) throws Exception;

    /**
     * Filtrar órdenes según su estado de pago.
     *
     * @param orderFilter Objeto dtoOrderFilter que contiene los criterios de filtrado.
     * @return Lista de órdenes que cumplen con el filtro aplicado.
     * @throws Exception Si ocurre un error durante el filtrado de órdenes.
     */
    List<Order> paymentFilterByState(dtoOrderFilter orderFilter) throws Exception;

}
