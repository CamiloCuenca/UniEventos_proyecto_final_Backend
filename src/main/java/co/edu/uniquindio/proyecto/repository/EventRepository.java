package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.model.Events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event,String> {
}
