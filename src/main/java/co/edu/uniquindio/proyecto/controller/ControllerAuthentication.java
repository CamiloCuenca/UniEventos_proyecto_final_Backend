package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ControllerAuthentication {

    private final AccountService accountService;
    private final EventService eventService;
    private final CouponService couponService;
    private final EventRepository eventRepository;
    private final OrderService orderService;

    // Account

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MessageDTO<String>> crearCuenta(@Valid @RequestBody createAccountDTO cuenta) throws Exception {
        accountService.createAccount(cuenta);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta creada exitosamente"));
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<MessageDTO<String>> activateAccount(@Valid @RequestBody ActivateAccountDTO activateAccountDTO) throws Exception {
        accountService.activateAccount(activateAccountDTO.getCorreo(), activateAccountDTO.getCode());
        return ResponseEntity.ok(new MessageDTO<>(false, "La cuenta se activó exitosamente"));
    }


    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MessageDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception {
        TokenDTO token = accountService.login(loginDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, token));
    }

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


    @GetMapping("/eventos-activos")
    public ResponseEntity<Map<String, Object>> getActiveEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        try {
            List<Event> events = new ArrayList<>();
            Pageable paging = PageRequest.of(page, size);

            // Busca los eventos que están activos
            Page<Event> pageEvents = eventRepository.findByStatus(EventStatus.ACTIVE, paging);
            events = pageEvents.getContent();

            if (events.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("events", events);
            response.put("currentPage", pageEvents.getNumber());
            response.put("totalItems", pageEvents.getTotalElements());
            response.put("totalPages", pageEvents.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/orden/recibir-notificacion")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> request){

        orderService.recibirNotificacionMercadoPago(request);
    }

    @GetMapping("/filter")
    public List<Event> filterEvents(@RequestParam(required = false) String name,
                                    @RequestParam(required = false) String city,
                                    @RequestParam(required = false) EventType type,
                                    @RequestParam(required = false) LocalDateTime date) {
        // Crear el DTO con los parámetros recibidos
        dtoEventFilter filter = new dtoEventFilter(name, type, city, date);

        // Imprimir los parámetros recibidos para depuración
        System.out.println("Parámetros recibidos - Name: " + filter.name() + ", City: " + filter.city() + ", Type: " + filter.type() + ", Date: " + filter.date());

        // Llamar al servicio con el DTO y retornar los resultados
        return eventService.eventFilter(filter);
    }


}
