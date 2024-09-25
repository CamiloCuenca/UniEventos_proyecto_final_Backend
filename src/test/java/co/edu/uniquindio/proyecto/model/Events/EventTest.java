package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.dto.Event.createDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.editDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.dtoEventFilter;
import co.edu.uniquindio.proyecto.dto.Event.dtoEventInformation;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventTest {
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;


    @Test
    public void createEvent() throws Exception {
        // Crear DTO para el nuevo evento
        createDTOEvent crearEventoDTO = new createDTOEvent(
                "rutaImg2",
                "Evento prueba 2",
                EventStatus.ACTIVE,
                "Prueba Creacion de Evento 2",
                "updated_image_localities.jpg",
                EventType.CONCERT,
                LocalDateTime.of(2024, 10, 20, 18, 0),
                "Armenia",
                Arrays.asList(
                        new Locality(50.0, "General", 100, 200),
                        new Locality(150.0, "VIP", 50, 100)
                )
        );

        // Llamar al método de servicio para crear el evento
        String eventId = eventService.createEvent(crearEventoDTO);

        // Verificar que el ID no sea nulo
        assertNotNull(eventId);


    }

    @Test
    public void UpdateEvent() throws Exception {

        editDTOEvent editarEventoDTO = new editDTOEvent(
                "66eb44b425a3ee6359359a2f", // El ID del evento a actualizar
                "image2.jpg",
                "Updated Event",
                EventStatus.INACTIVE,
                "Updated description",
                "updated_image_localities.jpg",
                EventType.CONCERT,
                LocalDateTime.of(2024, 10, 20, 18, 0),
                "Updated City",
                Arrays.asList(
                        new Locality(75.0, "General", 150, 250),
                        new Locality(200.0, "VIP", 80, 150)
                )
        );

        String re = eventService.editEvent(editarEventoDTO);

    }

    @Test
    public void DeleteEvent() throws Exception {
        // ID of the event that is assumed to already exist in the database
        String eventId = "66edcacb8ea93c339b2674e7";

        // Call service method to delete event
        String result = eventService.deleteEvent(eventId);


        // Verify that the event no longer exists in the repository
        Event deletedEvent = eventRepository.findById(eventId).orElse(null);
        assertNull(deletedEvent);
    }

    @Test
    public void obtainEventInformation() throws Exception {
        // ID del evento que se asume ya existe en la base de datos
        String eventId = "66edcc02b4652945128d2942";

        // Llamar al método de servicio para obtener la información del evento
        dtoEventInformation informacionEventoDTO = eventService.obtainEventInformation(eventId);

        // Verificar que el DTO no sea nulo
        assertNotNull(informacionEventoDTO);
    }

    @Test
    public void filterTest() throws Exception {
        dtoEventFilter filtroEventoDTO = new dtoEventFilter("", EventType.CONCERT, "");

        System.out.println(eventService.filterEvents(filtroEventoDTO));

    }

    @Test
    public void ListEvents() throws Exception {
        System.out.println(eventService.listEvents());
    }

}
