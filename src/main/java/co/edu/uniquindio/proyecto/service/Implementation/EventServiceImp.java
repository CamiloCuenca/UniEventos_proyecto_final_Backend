package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.exception.event.*;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;



import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService {

    @Autowired
    private MongoTemplate mongoTemplate;

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
        // Verificar si ya existe un evento con el mismo nombre y fecha
        if (nameExistsAndDate(crearEventoDTO.name(), crearEventoDTO.date())) {
            // Lanzar una excepción personalizada si se encuentra un evento existente
            throw new NameAndDateAlreadyExistsException(crearEventoDTO.name(), crearEventoDTO.date());
        }

        // Crear una nueva instancia de Event para almacenar los datos del evento
        Event newEvent = new Event();

        // Transferir los datos desde el DTO al objeto Event
        newEvent.setCoverImage(crearEventoDTO.coverImage()); // Establecer la imagen de portada
        newEvent.setName(crearEventoDTO.name()); // Establecer el nombre del evento
        newEvent.setDescription(crearEventoDTO.description()); // Establecer la descripción del evento
        newEvent.setDate(crearEventoDTO.date()); // Establecer la fecha del evento
        newEvent.setStatus(crearEventoDTO.status()); // Establecer el estado del evento
        newEvent.setImageLocalities(crearEventoDTO.imageLocalities()); // Establecer imágenes de localidades
        newEvent.setType(crearEventoDTO.type()); // Establecer el tipo de evento
        newEvent.setCity(crearEventoDTO.city()); // Establecer la ciudad del evento
        newEvent.setAddress(crearEventoDTO.address()); // Establecer la dirección del evento
        newEvent.setAmount(crearEventoDTO.amount()); // Establecer la cantidad de eventos
        newEvent.setLocalities(crearEventoDTO.localities()); // Establecer las localidades asociadas al evento

        // Guardar el nuevo evento en la base de datos
        Event createdEvent = eventRepository.save(newEvent);

        // Retornar el ID del evento creado
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
        // Buscar el evento existente por su ID
        Optional<Event> optionalEvent = eventRepository.findById(editarEventoDTO.id());

        // Verificar si el evento no fue encontrado
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(editarEventoDTO.id()); // Lanzar excepción si el evento no existe
        }

        // Obtener el evento que será modificado
        Event eventMondificado = optionalEvent.get();

        // Actualizar los datos del evento con los datos del DTO
        eventMondificado.setCoverImage(editarEventoDTO.coverImage()); // Actualizar imagen de portada
        eventMondificado.setName(editarEventoDTO.name()); // Actualizar nombre del evento
        eventMondificado.setStatus(editarEventoDTO.status()); // Actualizar estado del evento
        eventMondificado.setDescription(editarEventoDTO.description()); // Actualizar descripción del evento
        eventMondificado.setImageLocalities(editarEventoDTO.imageLocalities()); // Actualizar imágenes de localidades
        eventMondificado.setType(editarEventoDTO.type()); // Actualizar tipo de evento
        eventMondificado.setDate(editarEventoDTO.date()); // Actualizar fecha del evento
        eventMondificado.setCity(editarEventoDTO.city()); // Actualizar ciudad del evento
        eventMondificado.setAddress(editarEventoDTO.address()); // Actualizar dirección del evento
        eventMondificado.setAmount(editarEventoDTO.amount()); // Actualizar cantidad de eventos
        eventMondificado.setLocalities(editarEventoDTO.localities()); // Actualizar localidades asociadas al evento

        // Guardar el evento actualizado en la base de datos
        eventRepository.save(eventMondificado);

        // Retornar un mensaje de éxito
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
        // Buscar el evento existente por su ID
        Optional<Event> optionalEvent = eventRepository.findById(idEvent);

        // Verificar si el evento no fue encontrado
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(idEvent); // Lanzar excepción si el evento no existe
        }

        // Obtener el evento que será eliminado
        Event deletedEvent = optionalEvent.get();

        // Eliminar el evento de la base de datos
        //eventRepository.delete(deletedEvent);
        deletedEvent.setStatus(EventStatus.INACTIVE);// Nota: Se menciono que no se debe eliminar, sino cambiar a INACTIVO

        eventRepository.save(deletedEvent);
        // Retornar un mensaje de éxito
        return "El evento con id " + idEvent + " fue eliminado correctamente.";
    }


    /**
     * This method is the service of obtain events
     *
     * @param id
     * @return
     * @throws Exception
     */
    public dtoEventInformation obtainEventInformation(String id) throws EventNotFoundException {
        // Buscar el evento por su ID en el repositorio
        Optional<Event> optionalEvent = eventRepository.findById(id);

        // Verificar si el evento no fue encontrado
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(id); // Lanzar excepción si el evento no existe
        }

        // Obtener el evento que fue encontrado
        Event event = optionalEvent.get();

        // Retornar un objeto DTO con la información del evento
        return new dtoEventInformation(
                event.getCoverImage(),         // Imagen de portada del evento
                event.getName(),               // Nombre del evento
                event.getDescription(),        // Descripción del evento
                event.getImageLocalities(),    // Imágenes de las localidades
                event.getType(),               // Tipo de evento
                event.getDate(),               // Fecha del evento
                event.getCity(),               // Ciudad donde se lleva a cabo el evento
                event.getLocalities()          // Lista de localidades del evento
        );
    }


    /**
     * This method is the service of listing the events.
     *
     * @return Events
     */
    @Override
    public List<ItemEventDTO> listEvents() {
        try {
            // Recuperar la lista de eventos del repositorio
            List<Event> events = eventRepository.findAll();

            // Verificar si no se encontraron eventos
            if (events.isEmpty()) {
                throw new NoEventsFoundException(); // Lanzar excepción si no hay eventos
            }

            // Crear una lista para almacenar los DTOs de los eventos
            List<ItemEventDTO> items = new ArrayList<>();

            // Recorrer la lista de eventos y crear DTOs para cada uno
            for (Event event : events) {
                items.add(new ItemEventDTO(
                        event.getCoverImage(), // Imagen de portada del evento
                        event.getName(),       // Nombre del evento
                        event.getDate(),       // Fecha del evento
                        event.getAddress()     // Dirección del evento
                ));
            }

            // Retornar la lista de DTOs
            return items;
        } catch (Exception e) {
            // Capturar cualquier excepción y lanzar una excepción personalizada
            throw new EventRetrievalException(e.getMessage());
        }
    }


    @Override
    public List<eventosDTO> allEvents() throws Exception {
        try {
            // Recuperar la lista de eventos del repositorio
            List<Event> events = eventRepository.findAll();

            // Verificar si no se encontraron eventos
            if (events.isEmpty()) {
                throw new NoEventsFoundException(); // Lanzar excepción si no hay eventos
            }

            // Crear una lista para almacenar los DTOs de los eventos
            List<eventosDTO> items = new ArrayList<>();

            // Recorrer la lista de eventos y crear DTOs para cada uno
            for (Event event : events) {
                items.add(new eventosDTO(
                        event.getId(),
                        event.getCoverImage(), // Imagen de portada del evento
                        event.getName(),       // Nombre del evento
                        event.getStatus(),
                        event.getDescription(),
                        event.getImageLocalities(),
                        event.getType(),
                        event.getDate(),
                        event.getCity(),
                        event.getAddress(),
                        event.getAmount(),
                        event.getLocalities()
                ));
            }

            // Retornar la lista de DTOs
            return items;
        } catch (Exception e) {
            // Capturar cualquier excepción y lanzar una excepción personalizada
            throw new EventRetrievalException(e.getMessage());
        }
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
     *
     * @param idEvent
     * @return
     * @throws EventNotFoundException
     */
    @Override
    public double calculateTotal(String idEvent) throws EventNotFoundException {
        // Busca el evento en la base de datos utilizando el repositorio de eventos
        Event event = eventRepository.findById(idEvent)
                .orElseThrow(() -> new EventNotFoundException("No se encontró el evento con ID: " + idEvent));

        double total = 0.0; // Inicializa la variable total

        // Comprobar si las localidades no son nulas
        if (event.getLocalities() != null) {
            for (Locality locality : event.getLocalities()) {
                double priceUnit = locality.getPrice(); // Obtiene el precio de la localidad
                int ticketSold = locality.getTicketsSold(); // Obtiene el número de boletos vendidos
                total += priceUnit * ticketSold; // Sumar el total
            }
        }

        // Mostrar el total en la consola
        System.out.println("El total calculado para el evento con ID " + idEvent + " es: " + total);

        // Retornar el total calculado
        return total;
    }

    @Override
    public List<Event> eventFilter(dtoEventFilter filter) {
        Query query = new Query(); // Crea un nuevo objeto Query para la consulta
        List<Criteria> criteriaList = new ArrayList<>(); // Lista para almacenar los criterios de búsqueda

        // Agregar criterios dinámicos según los filtros proporcionados
        if (filter.name() != null && !filter.name().isEmpty()) {
            String normalizedName = filter.name().trim(); // Normaliza el nombre
            criteriaList.add(Criteria.where("name").regex(".*" + normalizedName + ".*", "i")); // Agrega un criterio de expresión regular para el nombre
        }
        if (filter.city() != null && !filter.city().isEmpty()) {
            String normalizedCity = filter.city().trim(); // Normaliza la ciudad
            criteriaList.add(Criteria.where("city").regex(".*" + normalizedCity + ".*", "i")); // Agrega un criterio de expresión regular para la ciudad
        }
        if (filter.type() != null) {
            criteriaList.add(Criteria.where("type").is(filter.type())); // Agrega un criterio de igualdad para el tipo
        }
        if (filter.date() != null) {
            criteriaList.add(Criteria.where("date").is(filter.date())); // Agrega un criterio de igualdad para la fecha
        }

        // Si hay criterios, agrégarlos a la consulta
        if (!criteriaList.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteriaList.toArray(new Criteria[0]))); // Agrega todos los criterios a la consulta
        }

        return mongoTemplate.find(query, Event.class); // Ejecuta la consulta y retorna los eventos filtrados
    }


}
