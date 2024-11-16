package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.City;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.exception.account.*;
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
import java.util.*;

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

    @PostMapping("/cuenta/crear-cuenta")
    public ResponseEntity<MessageDTO<String>> crearCuenta(@Valid @RequestBody createAccountDTO cuenta) {
        try {
            accountService.createAccount(cuenta);
            return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta creada exitosamente"));
        } catch (EmailAlreadyExistsException e) {
            // Manejar la excepción de email ya existente
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageDTO<>(true, "El email electrónico ya está en uso."));
        } catch (CedulaAlreadyExistsException e) {
            // Manejar la excepción de cédula ya existente
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new MessageDTO<>(true, "El número de identificación ya está en uso."));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/cuenta/activar-cuenta")
    public ResponseEntity<MessageDTO<String>> activateAccount(@Valid @RequestBody ActivateAccountDTO activateAccountDTO) throws Exception {
        accountService.activateAccount(activateAccountDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "La cuenta se activó exitosamente"));
    }



    @PostMapping("/cuenta/iniciar-sesion")
    public ResponseEntity<MessageDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            TokenDTO token = accountService.login(loginDTO);
            return ResponseEntity.ok(new MessageDTO<>(false, token));
        } catch (ActiveAccountException e) {
            // Devolver código de estado 423 (Locked) cuando la cuenta esté bloqueada
            return ResponseEntity.status(HttpStatus.LOCKED)
                    .body(new MessageDTO<>(true, null));
        } catch (EmailNotFoundException e) {
            // Devolver código de estado 404 si el email no es encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDTO<>(true, null));
        } catch (InvalidPasswordException e) {
            // Devolver código de estado 401 si la contraseña es incorrecta
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageDTO<>(true, null));
        } catch (Exception e) {
            // Devolver código de estado 500 para errores generales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO<>(true,  null));
        }
    }

    // Obtener información de un evento por su ID
    @GetMapping("/evento/obtener-info/{id}")
    public ResponseEntity<MessageDTO<dtoEventInformation>> obtainEventInformation(@PathVariable String id) throws Exception {
        dtoEventInformation eventInfo = eventService.obtainEventInformation(id);
        return ResponseEntity.ok(new MessageDTO<>(false, eventInfo));
    }

    // Listar todos los eventos
    @GetMapping("/evento/listar-eventos")
    public ResponseEntity<MessageDTO<List<ItemEventDTO>>> listEvents() {
        List<ItemEventDTO> events = eventService.listEvents();
        return ResponseEntity.ok(new MessageDTO<>(false, events));
    }


    @GetMapping("/evento/eventos-activos")
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

    @GetMapping("/evento/filter")
    public List<ItemEventDTO> filterEvents(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) City city,
                                           @RequestParam(required = false) EventType type,
                                           @RequestParam(required = false) LocalDateTime date,
                                           @RequestParam(required = false) EventStatus status) {
        // Crear el DTO con los parámetros recibidos
        dtoEventFilter filter = new dtoEventFilter(name, type, status, city, date);

        // Imprimir los parámetros recibidos para depuración
        System.out.println("Parámetros recibidos - Name: " + filter.name() + ", City: " + filter.city() + ", Type: " + filter.type() + ", Date: " + filter.date());

        // Llamar al servicio con el DTO y retornar los resultados (ahora se retornan ItemEventDTOs)
        return eventService.eventFilter(filter);
    }

}
