package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.model.Events.Event;

import java.util.List;

public interface EventService {

    String createEvent(createDTOEvent crearEventoDTO) throws Exception;

    String editEvent(editDTOEvent editarEventoDTO) throws Exception;

    String deleteEvent(String id) throws Exception;

    dtoEventInformation obtainEventInformation(String id) throws Exception;

    List<ItemEventDTO> listEvents();


    List<ItemEventDTO> filterEvents(dtoEventFilter filtroEventoDTO);
}
