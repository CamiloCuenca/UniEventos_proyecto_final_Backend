package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Carts.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart,String> {
}
