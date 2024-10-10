package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;

import java.util.List;

public interface EventService {

    /**
     * Crear un evento
     *
     * @param crearEventoDTO Objeto DTO que contiene la información para crear el evento.
     * @return String que podría ser el ID del evento creado o un mensaje de éxito.
     * @throws Exception Si ocurre algún error durante la creación del evento.
     */
    String createEvent(createDTOEvent crearEventoDTO) throws Exception;

    /**
     * Editar un evento
     *
     * @param editarEventoDTO Objeto DTO que contiene los datos actualizados del evento.
     * @return String con un mensaje de éxito o confirmación.
     * @throws Exception Si ocurre un error durante la edición del evento.
     */
    String editEvent(editDTOEvent editarEventoDTO) throws Exception;

    /**
     * Eliminar un evento
     *
     * @param idEvent ID del evento que se desea eliminar.
     * @return String con un mensaje de confirmación de la eliminación.
     * @throws Exception Si no se puede eliminar el evento.
     */
    String deleteEvent(String idEvent) throws Exception;

    /**
     * Obtener la información de un evento por su ID
     *
     * @param id ID del evento.
     * @return dtoEventInformation que contiene la información completa del evento.
     * @throws Exception Si no se encuentra el evento.
     */
    dtoEventInformation obtainEventInformation(String id) throws Exception;

    /**
     * Listar todos los eventos
     *
     * @return Lista de ItemEventDTO que contiene información resumida de los eventos.
     */
    List<ItemEventDTO> listEvents();

    /**
     * Obtener un evento por su ID
     *
     * @param idEvent ID del evento.
     * @return El evento encontrado.
     * @throws Exception Si no se encuentra el evento.
     */
    Event obtenerEvento(String idEvent) throws Exception;

    /**
     * Calcular el total de un evento
     *
     * @param idEvent ID del evento.
     * @return El total calculado del evento.
     * @throws Exception Si no se puede calcular el total.
     */
    double calculateTotal(String idEvent) throws Exception;

    /**
     * Filtrar eventos según ciertos criterios
     *
     * @param filter Objeto dtoEventFilter que contiene los criterios de filtrado (ej. fecha, categoría, etc.).
     * @return Lista de eventos que cumplen con el filtro aplicado.
     */
    List<Event> eventFilter(dtoEventFilter filter);
}

