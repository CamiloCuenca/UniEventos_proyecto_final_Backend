package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;

import java.util.List;

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
}
