package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends MongoRepository<Event,String> {

    @Query("{"
            + "'$or': ["
            + "  { 'name' : { $regex: ?0, $options: 'i' } },"
            + "  { 'city' : ?1 },"
            + "  { 'type' : ?2 }"
            + "]"
            + "}")
    List<Event> findByFiltros(String nombre, String ciudad, EventType tipo);

    @Query("{'_id': ?0}")
    Optional<Account> findAllById(String id);
}
