package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;

import java.util.List;

public interface OrderService {

    // Crear una nueva orden
    Order createOrder(Order order) throws Exception;

    // Actualizar una orden existente
    Order updateOrder(String orderId, Order updatedOrder) throws Exception;

    // Eliminar una orden
    void deleteOrder(String orderId) throws Exception;

    // Obtener una orden por su ID
    Order getOrderById(String orderId) throws Exception;

    // Listar todas las órdenes de un usuario específico
    //List<Order> getOrdersByUser(String userId) throws Exception;

    // Listar todas las órdenes
    List<Order> getAllOrders() throws Exception;
}
