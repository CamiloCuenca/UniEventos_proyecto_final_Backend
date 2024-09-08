package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Accounts.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
