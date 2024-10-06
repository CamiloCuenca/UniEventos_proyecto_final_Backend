package co.edu.uniquindio.proyecto.model.Events;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.Enum.Localities;
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

    // Inyección de dependencias del servicio de eventos y repositorio de eventos
    @Autowired
    private EventService eventService;
    @Autowired
    private EventRepository eventRepository;

    // Prueba para crear un evento
    @Test
    public void createEvent() throws Exception {
        // Crear DTO para el nuevo evento con datos de prueba
        createDTOEvent crearEventoDTO = new createDTOEvent(
                "rutaImg2.png",                   // Imagen del evento
                "Evento prueba 1",                // Nombre del evento
                EventStatus.ACTIVE,               // Estado del evento
                "Prueba Creación de Evento 1",    // Descripción del evento
                "updated_image_localities.jpg",   // Imagen de localidades
                EventType.CONCERT,                // Tipo de evento
                LocalDateTime.of(2024, 10, 20, 18, 0), // Fecha y hora del evento
                "Bogota",                        // Ciudad del evento
                "crr 20 # 1-23",                  // Dirección del evento
                600,                              // Capacidad total
                Arrays.asList(                    // Lista de localidades
                        new Locality(50.0, Localities.GENERAL, 100, 200),
                        new Locality(150.0, Localities.VIP, 50, 100)
                )
        );

        // Llamar al método de servicio para crear el evento
        String eventId = eventService.createEvent(crearEventoDTO);

        // Verificar que el ID generado del evento no sea nulo
        assertNotNull(eventId);
    }

    // Prueba para actualizar un evento existente
    @Test
    public void UpdateEvent() throws Exception {
        // Crear DTO para editar un evento existente con nuevos datos
        editDTOEvent editarEventoDTO = new editDTOEvent(
                "66eb44b425a3ee6359359a2f",       // ID del evento a actualizar
                "image2.jpg",                     // Nueva imagen del evento
                "Updated Event",                  // Nombre actualizado del evento
                EventStatus.INACTIVE,             // Estado actualizado del evento
                "Updated description",            // Descripción actualizada
                "updated_image_localities.jpg",   // Imagen actualizada de localidades
                EventType.CONCERT,                // Tipo de evento (concierto)
                LocalDateTime.of(2024, 10, 20, 18, 0), // Fecha y hora del evento
                "Updated City",                   // Ciudad actualizada
                "crr 20 # 1-23",                  // Dirección actualizada
                600,                              // Capacidad total
                Arrays.asList(                    // Lista de localidades actualizadas
                        new Locality(75.0, Localities.GENERAL, 150, 250),
                        new Locality(200.0, Localities.VIP, 80, 150)
                )
        );

        // Llamar al servicio para actualizar el evento
        String re = eventService.editEvent(editarEventoDTO);
    }

    // Prueba para eliminar un evento
    @Test
    public void DeleteEvent() throws Exception {
        // ID del evento que se asume ya existe en la base de datos
        String eventId = "66f5c5a0de22e82833106d92";

        // Llamar al método del servicio para eliminar el evento
        String result = eventService.deleteEvent(eventId);

        // Verificar que el evento haya sido eliminado comprobando que ya no está en el repositorio
        Event deletedEvent = eventRepository.findById(eventId).orElse(null);
        assertNull(deletedEvent);  // El evento debería ser nulo ya que fue eliminado
    }

    // Prueba para obtener la información de un evento
    @Test
    public void obtainEventInformation() throws Exception {
        // ID del evento que se asume ya existe en la base de datos
        String eventId = "66edcc02b4652945128d2942";

        // Llamar al método de servicio para obtener la información del evento
        dtoEventInformation informacionEventoDTO = eventService.obtainEventInformation(eventId);

        // Verificar que el DTO con la información del evento no sea nulo
        assertNotNull(informacionEventoDTO);
    }

    // Prueba para filtrar eventos
    @Test
    public void filterTest() throws Exception {
        // Crear DTO de filtro de eventos basado en tipo de evento y otros parámetros
        dtoEventFilter filtroEventoDTO = new dtoEventFilter("", EventType.CONCERT, "");

        // Imprimir los eventos filtrados
        System.out.println(eventService.filterEvents(filtroEventoDTO));
    }

    // Prueba para listar todos los eventos
    @Test
    public void ListEvents() throws Exception {
        // Imprimir la lista de todos los eventos
        System.out.println(eventService.listEvents());
    }

    @Test
    public void calculateTotalTestPrice() throws Exception{
        String idEvent = "66fe4a3d6e8bef16ed873d70";
        eventService.calculateTotal(idEvent);



    }
}