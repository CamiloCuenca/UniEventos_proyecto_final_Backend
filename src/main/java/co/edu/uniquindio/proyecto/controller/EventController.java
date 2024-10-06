package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;


    // Obtener información de un evento por su ID
    @GetMapping("/obtener-info/{id}")
    public ResponseEntity<MessageDTO<dtoEventInformation>> obtainEventInformation(@PathVariable String id) throws Exception {
        dtoEventInformation eventInfo = eventService.obtainEventInformation(id);
        return ResponseEntity.ok(new MessageDTO<>(false, eventInfo));
    }

    // Listar todos los eventos
    @GetMapping("/listar-eventos")
    public ResponseEntity<MessageDTO<List<ItemEventDTO>>> listEvents() {
        List<ItemEventDTO> events = eventService.listEvents();
        return ResponseEntity.ok(new MessageDTO<>(false, events));
    }

    // Filtrar eventos según ciertos criterios
    @PostMapping("/filtrar-eventos")
    public ResponseEntity<MessageDTO<List<ItemEventDTO>>> filterEvents(@Valid @RequestBody dtoEventFilter filtroEventoDTO) {
        List<ItemEventDTO> filteredEvents = eventService.filterEvents(filtroEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, filteredEvents));
    }


}
