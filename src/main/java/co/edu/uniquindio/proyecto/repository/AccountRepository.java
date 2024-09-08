package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Accounts.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {

}
