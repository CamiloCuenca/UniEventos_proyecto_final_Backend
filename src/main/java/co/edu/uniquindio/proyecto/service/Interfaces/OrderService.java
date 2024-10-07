package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import com.mercadopago.resources.preference.Preference;

import java.util.List;
import java.util.Map;

public interface OrderService {

    // Crear una nueva orden
    Order createOrder(OrderDTO orderDTO) throws Exception;

    // Actualizar una orden existente
    Order updateOrder(String orderId, OrderDTO updatedOrderDTO ) throws Exception;

    // Eliminar una orden
    void deleteOrder(String orderId) throws Exception;

    // Obtener una orden por su ID
    Order getOrderById(String orderId) throws Exception;

    // Listar todas las órdenes de una cuenta específica
    List<Order> getOrdersByUser(String accountId ) throws Exception;

    // Listar todas las órdenes
    List<Order> getAllOrders() throws Exception;

    Preference realizarPago(String idOrden) throws Exception;
    void recibirNotificacionMercadoPago(Map<String, Object> request);

    public Order obtenerOrden(String idOrden) throws Exception;
}
