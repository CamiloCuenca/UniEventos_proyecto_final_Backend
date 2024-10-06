package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.Event.*;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ControllerAuthentication {

    private final AccountService accountService;
    private final EventService eventService;
    private final CouponService couponService;

    // Account

    @PostMapping("/crear-cuenta")
    public ResponseEntity<MessageDTO<String>> crearCuenta(@Valid @RequestBody createAccountDTO cuenta) throws Exception{
        accountService.createAccount(cuenta);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta creada exitosamente"));
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<MessageDTO<String>> activateAccount(@Valid @RequestBody ActivateAccountDTO activateAccountDTO) throws Exception {
        accountService.activateAccount(activateAccountDTO.getCorreo(), activateAccountDTO.getCode());
        return ResponseEntity.ok(new MessageDTO<>(false, "La cuenta se activó exitosamente"));
    }


    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MessageDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
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

    // Filtrar eventos según ciertos criterios
    @PostMapping("/filtrar-eventos")
    public ResponseEntity<MessageDTO<List<ItemEventDTO>>> filterEvents(@Valid @RequestBody dtoEventFilter filtroEventoDTO) {
        List<ItemEventDTO> filteredEvents = eventService.filterEvents(filtroEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, filteredEvents));
    }








}
