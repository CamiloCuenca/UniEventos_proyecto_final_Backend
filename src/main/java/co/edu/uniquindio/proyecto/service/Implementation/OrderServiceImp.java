package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImp implements OrderService {

  @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(Order order) throws Exception {

        if(order == null){
            throw new Exception("No se puede crear un orden");
        }
        return orderRepository.save(order);
    }

    @Override
    public Order updateOrder(String orderId, Order updatedOrder) throws Exception {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if(existingOrder.isEmpty()){
            throw  new Exception("No se puede actualizar un orden");
        }
        // Actualizar los campos necesarios
        Order order = existingOrder.get();
        order.setTotal(updatedOrder.getTotal());
        order.setItems(updatedOrder.getItems());
        // Actualizar la orden en la base de datos
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()){
            throw new Exception("No se puede eliminar la orden");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getOrderById(String orderId) throws Exception {
        // Buscar la orden por su ID
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("La orden no existe"));

    }

    @Override
    public List<Order> getOrdersByUser(String userId) throws Exception {
        return orderRepository.findByidUser(userId);
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        return orderRepository.findAll();
    }
}
