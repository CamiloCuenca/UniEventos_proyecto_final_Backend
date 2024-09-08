package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

import static com.mongodb.assertions.Assertions.assertNull;

@RequiredArgsConstructor
@Service
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;

    @Override
    public String crearEvento(CrearEventoDTO crearEventoDTO) throws Exception {
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

    @Override
    public String editarEvento(EditarEventoDTO editarEventoDTO) throws Exception {

        // Search for the existing event by its id
        Event event = eventRepository.findById(editarEventoDTO.id()) // Asegúrate de tener el ID en el DTO
                .orElseThrow(() -> new Exception("El evento no existe"));


        // Update event data with DTO data
        event.setCoverImage(editarEventoDTO.coverImage());
        event.setName(editarEventoDTO.name());
        event.setStatus(editarEventoDTO.status());
        event.setDescription(editarEventoDTO.description());
        event.setImageLocalities(editarEventoDTO.imageLocalities());
        event.setType(editarEventoDTO.type());
        event.setDate(editarEventoDTO.date());
        event.setCity(editarEventoDTO.city());
        event.setLocalities(editarEventoDTO.localities());

        // Save the updated event
        eventRepository.save(event);

        return "El evento ha sido actualizado correctamente";
    }

    @Override
    public String eliminarEvento(String id) throws Exception {
        // Check if the event exists before trying to delete it
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new Exception("El evento no existe"));

        // Delete the event
        eventRepository.deleteById(id);

        return "El evento se eliminó correctamente";
    }

    @Override
    public InformacionEventoDTO obtenerInformacionEvento(String id) throws Exception {
        //Search the event by id
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new Exception("El evento no existe"));
        // Mapear el evento al DTO InformacionEventoDTO

        return new InformacionEventoDTO(
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

    @Override
    public List<ItemEventoDTO> listarEventos() {
        return List.of();
    }

    @Override
    public List<ItemEventoDTO> filtrarEventos(FiltroEventoDTO filtroEventoDTO) {
        return List.of();
    }
}
