package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.exception.event.*;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        newEvent.setCoverImage(crearEventoDTO.coverImage());
        newEvent.setName(crearEventoDTO.name());
        newEvent.setDescription(crearEventoDTO.description());
        newEvent.setDate(crearEventoDTO.date());
        newEvent.setStatus(crearEventoDTO.status());
        newEvent.setImageLocalities(crearEventoDTO.imageLocalities());
        newEvent.setType(crearEventoDTO.type());
        newEvent.setCity(crearEventoDTO.city());
        newEvent.setAddress(crearEventoDTO.address());
        newEvent.setAmount(crearEventoDTO.amount());
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
        eventMondificado.setAddress(editarEventoDTO.address());
        eventMondificado.setAmount(editarEventoDTO.amount());
        eventMondificado.setLocalities(editarEventoDTO.localities());

        // Save the updated event
        eventRepository.save(eventMondificado);

        return "El evento ha sido actualizado correctamente";
    }

    /**
     * This method is the service of delete a event
     *
     * @param idEvent
     * @return
     * @throws Exception
     */
    @Override
    public String deleteEvent(String idEvent) throws EventNotFoundException {
        Optional<Event> optionalEvent = eventRepository.findById(idEvent);
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(idEvent);
        }

        // Eliminar el evento si se encuentra
        Event deletedEvent = optionalEvent.get();
        eventRepository.delete(deletedEvent);  // no eliminarlos cambiarlos a INACTIVOS

        return "El evento con id " + idEvent + " fue eliminado correctamente.";
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

    @Override
    public Event obtenerEvento(String idEvent) throws Exception {
        // Verifica que el idEvent no sea nulo o vacío
        if (idEvent == null || idEvent.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del evento no puede estar vacío.");
        }

        // Busca el evento en la base de datos utilizando el repositorio de eventos
        Optional<Event> eventoOptional = eventRepository.findById(idEvent);

        // Si el evento no se encuentra, lanza una excepción
        if (eventoOptional.isEmpty()) {
            throw new Exception("Evento no encontrado con el ID proporcionado: " + idEvent);
        }

        // Retorna el evento encontrado
        return eventoOptional.get();
    }





    /**
     * Metodo para calcular el total de tickets vendios.
     * @param idEvent
     * @return
     * @throws EventNotFoundException
     */
    @Override
    public double calculateTotal(String idEvent) throws EventNotFoundException {
        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new EventNotFoundException("No se encontró el evento con ID: " + idEvent));

        double total = 0.0;

        // Comprobar si las localidades no son nulas
        if (event.getLocalities() != null) {
            for (Locality locality : event.getLocalities()) {
                double priceUnit = locality.getPrice();
                int ticketSold = locality.getTicketsSold();
                total += priceUnit * ticketSold; // Sumar el total
            }
        }

        // Mostrar el total en la consola
        System.out.println("El total calculado para el evento con ID " + idEvent + " es: " + total);

        // Retornar el total calculado
        return total;
    }


    public Event getById(String id) throws Exception {
        return eventRepository.findById(id).orElseThrow(() -> new Exception("El evento No existe"));
    }

}
