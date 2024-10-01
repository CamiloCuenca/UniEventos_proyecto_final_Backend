package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Carts.Cart;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart,String> {
    @Query("{'accountId': ?0}")
    Optional<Cart> findAById (String account);
    @Query("{'_id': ?0}")
    Optional<Cart> findByIdAccount(String accountId);
}
