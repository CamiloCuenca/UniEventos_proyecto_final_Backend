package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.exception.event.*;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.naming.NameAlreadyBoundException;
import javax.security.auth.login.AccountNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;

    private boolean nameExistsAndDate(String name, LocalDateTime dateTime) {
        return eventRepository.findAllByNameAndDate(name, dateTime).isPresent();
    }

    /**
     * This method is the service of a create  new event
     *
     * @param crearEventoDTO
     * @return eventID
     * @throws Exception
     */
    @Override
    public String createEvent(createDTOEvent crearEventoDTO) throws NameAndDateAlreadyExistsException {

        if (nameExistsAndDate(crearEventoDTO.name(), crearEventoDTO.date())) {
            throw new NameAndDateAlreadyExistsException(crearEventoDTO.name(), crearEventoDTO.date());
        }
        // Mapping (transferring) the data from the DTO to an object of type Event
        Event newEvent = new Event();
        newEvent.setName(crearEventoDTO.name());
        newEvent.setDescription(crearEventoDTO.description());
        newEvent.setDate(crearEventoDTO.date());
        newEvent.setStatus(crearEventoDTO.status());
        newEvent.setImageLocalities(crearEventoDTO.imageLocalities());
        newEvent.setType(crearEventoDTO.type());
        newEvent.setCity(crearEventoDTO.city());
        newEvent.setLocalities(crearEventoDTO.localities());

        // save user account to database
        Event createdEvent = eventRepository.save(newEvent);
        return createdEvent.getId();
    }

    /**
     * This method is the service of edit a event
     *
     * @param editarEventoDTO
     * @return
     * @throws Exception
     */

    @Override
    public String editEvent(editDTOEvent editarEventoDTO) throws EventNotFoundException {

        Optional<Event> optionalEvent = eventRepository.findById(editarEventoDTO.id());
        // Search for the existing event by its id
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(editarEventoDTO.id());
        }
        Event eventMondificado = optionalEvent.get();

        // Update event data with DTO data
        eventMondificado.setCoverImage(editarEventoDTO.coverImage());
        eventMondificado.setName(editarEventoDTO.name());
        eventMondificado.setStatus(editarEventoDTO.status());
        eventMondificado.setDescription(editarEventoDTO.description());
        eventMondificado.setImageLocalities(editarEventoDTO.imageLocalities());
        eventMondificado.setType(editarEventoDTO.type());
        eventMondificado.setDate(editarEventoDTO.date());
        eventMondificado.setCity(editarEventoDTO.city());
        eventMondificado.setLocalities(editarEventoDTO.localities());

        // Save the updated event
        eventRepository.save(eventMondificado);

        return "El evento ha sido actualizado correctamente";
    }

    /**
     * This method is the service of delete a event
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public String deleteEvent(String id) throws EventNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(id);
        }

        // Eliminar el evento si se encuentra
        Event deletedEvent = optionalEvent.get();
        eventRepository.delete(deletedEvent);

        return "El evento con id " + id + " fue eliminado correctamente.";
    }


    /**
     * This method is the service of obtain events
     *
     * @param id
     * @return
     * @throws Exception
     */
    public dtoEventInformation obtainEventInformation(String id) throws EventNotFoundException{
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(id);
        }
        Event event = optionalEvent.get();
        return new dtoEventInformation(
                event.getCoverImage(),
                event.getName(),
                event.getDescription(),
                event.getImageLocalities(),
                event.getType(),
                event.getDate(),
                event.getCity(),
                event.getLocalities()
        );
    }

    /**
     * This method is the service of listing the events.
     *
     * @return Events
     */
    @Override
    public List<ItemEventDTO> listEvents() {
        try{
            List<Event> events = eventRepository.findAll();
            if (events.isEmpty()){
                throw new NoEventsFoundException();
            }
            List<ItemEventDTO> items = new ArrayList<>();
            for (Event event : events) {
                items.add(new ItemEventDTO(
                        event.getCoverImage(),
                        event.getName(),
                        event.getDate(),
                        event.getAddress()
                ));
            }
            return items;
        }catch (Exception e){
            throw new EventRetrievalException(e.getMessage());
        }

    }

    /**
     * This method is the service of filtering the events
     *
     * @param filtroEventoDTO
     * @return dtoEventFilter
     */
    @Override
    public List<ItemEventDTO> filterEvents(dtoEventFilter filtroEventoDTO) {

        List<Event> filteredEvents = eventRepository.findByFiltros(
                filtroEventoDTO.name(),
                filtroEventoDTO.city(),
                filtroEventoDTO.type()
        );

        return filteredEvents.stream()
                .map(event -> new ItemEventDTO(
                        event.getCoverImage(),
                        event.getName(),
                        event.getDate(),
                        event.getAddress()
                ))
                .collect(Collectors.toList());
    }

    public Event getById(String id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new Exception("El evento No existe"));
    }

}
