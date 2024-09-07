package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order,String> {
}
