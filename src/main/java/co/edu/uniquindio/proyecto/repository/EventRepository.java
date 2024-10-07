package co.edu.uniquindio.proyecto.repository;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Events.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    Page<Event> findByStatus(EventStatus status, Pageable pageable);

    Optional<Event> findAllById(String id);


    @Query("{'name': ?0}" + "{'date': ?1}")
    Optional<Account> findAllByNameAndDate(String name, LocalDateTime date);

    long countByStatus(EventStatus status);


}
