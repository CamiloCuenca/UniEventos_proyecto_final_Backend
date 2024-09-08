package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.dto.Event.CrearEventoDTO;
import co.edu.uniquindio.proyecto.dto.Event.EditarEventoDTO;
import co.edu.uniquindio.proyecto.dto.Event.InformacionEventoDTO;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Implementation.EventServiceImp;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventTest {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private EventServiceImp eventService;

    @Test
    public void createEvent() throws Exception {
        // Crear DTO para el nuevo evento
        CrearEventoDTO crearEventoDTO = new CrearEventoDTO(
                "rutaImg2",
                "Evento prueba 2",
                EventStatus.ACTIVO,
                "Prueba Creacion de Evento 2",
                "updated_image_localities.jpg",
                EventType.CONCIERTO,
                LocalDateTime.of(2024, 10, 20, 18, 0),
                "Armenia",
                Arrays.asList(
                        new Locality(50.0, "General", 100, 200),
                        new Locality(150.0, "VIP", 50, 100)
                )
        );

        // Llamar al método de servicio para crear el evento
        String eventId = eventService.crearEvento(crearEventoDTO);

        // Verificar que el ID no sea nulo
        assertNotNull(eventId);


    }

    @Test
    public void UpdateEvent() throws Exception {

        EditarEventoDTO editarEventoDTO = new EditarEventoDTO(
                "66dcf9d99b293d0c2aba1370", // El ID del evento a actualizar
                "image2.jpg",
                "Updated Event",
                EventStatus.INACTIVO,
                "Updated description",
                "updated_image_localities.jpg",
                EventType.CONCIERTO,
                LocalDateTime.of(2024, 10, 20, 18, 0),
                "Updated City",
                Arrays.asList(
                        new Locality(75.0, "General", 150, 250),
                        new Locality(200.0, "VIP", 80, 150)
                )
        );

        String re = eventService.editarEvento(editarEventoDTO);

    }

    @Test
    public void DeleteEvent() throws Exception {
        // ID of the event that is assumed to already exist in the database
        String eventId = "66dd0e1c113a9b5dcb6be87e";

        // Call service method to delete event
        String result = eventService.eliminarEvento(eventId);

        // Verify that the delete message is correct // provisional
        assertEquals("El evento se eliminó correctamente", result);

        // Verify that the event no longer exists in the repository
        Event deletedEvent = eventRepository.findById(eventId).orElse(null);
        assertNull(deletedEvent);
    }

    @Test
    public void obtenerInformacionEvento() throws Exception {
        // ID del evento que se asume ya existe en la base de datos
        String eventId = "66dcf9d99b293d0c2aba1370";

        // Llamar al método de servicio para obtener la información del evento
        InformacionEventoDTO informacionEventoDTO = eventService.obtenerInformacionEvento(eventId);

        // Verificar que el DTO no sea nulo
        assertNotNull(informacionEventoDTO);
    }
}
