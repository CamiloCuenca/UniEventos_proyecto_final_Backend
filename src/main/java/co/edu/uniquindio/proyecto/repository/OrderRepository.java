package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String> {

    List<Order> findByidUser(String userId);
}
