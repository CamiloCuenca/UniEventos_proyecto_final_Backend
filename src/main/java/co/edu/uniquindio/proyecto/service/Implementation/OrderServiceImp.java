package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service  // Marca esta clase como un servicio de Spring, lo que permite la inyección de dependencias y otras características de Spring.
public class OrderServiceImp implements OrderService {

    @Autowired  // Inyección de dependencias para el repositorio que maneja las órdenes.
    OrderRepository orderRepository;

    // Método para crear una nueva orden
    @Override
    public Order createOrder(Order order) throws Exception {

        if(order == null){  // Verifica que la orden no sea nula antes de proceder.
            throw new Exception("No se puede crear una orden");  // Si la orden es nula, lanza una excepción.
        }
        return orderRepository.save(order);  // Guarda la orden en la base de datos y la devuelve.
    }

    // Método para actualizar una orden existente
    @Override
    public Order updateOrder(String orderId, Order updatedOrder) throws Exception {
        Optional<Order> existingOrder = orderRepository.findById(orderId);  // Busca la orden por su ID.
        if(existingOrder.isEmpty()){  // Si no existe, lanza una excepción.
            throw new Exception("No se puede actualizar una orden");
        }
        // Si la orden existe, se obtienen los datos de la orden y se actualizan los campos necesarios.
        Order order = existingOrder.get();
        order.setTotal(updatedOrder.getTotal());  // Actualiza el total de la orden.
        order.setItems(updatedOrder.getItems());  // Actualiza los ítems de la orden.

        // Guarda los cambios en la base de datos y devuelve la orden actualizada.
        return orderRepository.save(order);
    }

    // Método para eliminar una orden por su ID
    @Override
    public void deleteOrder(String orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);  // Busca la orden por su ID.
        if(order.isEmpty()){  // Si la orden no existe, lanza una excepción.
            throw new Exception("No se puede eliminar la orden");
        }
        orderRepository.deleteById(orderId);  // Elimina la orden de la base de datos por su ID.
    }

    // Método para obtener una orden específica por su ID
    @Override
    public Order getOrderById(String orderId) throws Exception {
        // Busca la orden y si no la encuentra, lanza una excepción.
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("La orden no existe"));
    }

    // Método para obtener todas las órdenes de un usuario específico
    @Override
    public List<Order> getOrdersByUser(String accountId) throws Exception {
        // Busca todas las órdenes asociadas a la cuenta del usuario.
        return orderRepository.findByAccountId(accountId);
    }

    // Método para obtener todas las órdenes en el sistema
    @Override
    public List<Order> getAllOrders() throws Exception {
        // Recupera todas las órdenes de la base de datos.
        return orderRepository.findAll();
    }
}
