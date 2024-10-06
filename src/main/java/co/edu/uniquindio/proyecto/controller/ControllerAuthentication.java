package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.Event.createDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.editDTOEvent;
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

    @PutMapping("/editar-perfil/{id}")
    public ResponseEntity<MessageDTO<String>> editAccount(@Valid @RequestBody editAccountDTO cuenta , @PathVariable String id) throws Exception{
        accountService.editAccount(cuenta,id);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta editada exitosamente"));

    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<MessageDTO<String>> deleteAccount(@PathVariable String id) throws Exception{
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta eliminada exitosamente"));
    }


    @GetMapping("/listar-cuentas")
    public ResponseEntity<MessageDTO<List<dtoAccountItem>>> listAccounts() {
        List<dtoAccountItem> accounts = accountService.listAccounts();
        return ResponseEntity.ok(new MessageDTO<>(false, accounts));
    }


    @PostMapping("/enviar-codigo/{correo}")
    public ResponseEntity<MessageDTO<String>>  sendPasswordRecoveryCode(@PathVariable String correo) throws Exception{
        accountService.sendPasswordRecoveryCode(correo);
        return ResponseEntity.ok(new MessageDTO<>(true, "Se envio el codigo exitosamente"));
    }

    @PutMapping("/cambiar-contrsena/{correo}")
    public ResponseEntity<MessageDTO<String>> changePassword(@Valid @RequestBody changePasswordDTO changePasswordDTO, @PathVariable String correo) throws Exception{
        accountService.changePassword(changePasswordDTO, correo);
        return ResponseEntity.ok(new MessageDTO<>(true, "Se cambio la contraseña exitosamente"));
    }


    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MessageDTO<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
        TokenDTO token = accountService.login(loginDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, token));
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<MessageDTO<String>> activateAccount(@Valid @RequestBody ActivateAccountDTO activateAccountDTO) throws Exception {
        accountService.activateAccount(activateAccountDTO.getCorreo(), activateAccountDTO.getCode());
        return ResponseEntity.ok(new MessageDTO<>(false, "La cuenta se activó exitosamente"));
    }



    // Event

    // Crear un evento
    @PostMapping("/crear-evento")
    public ResponseEntity<MessageDTO<String>> createEvent(@Valid @RequestBody createDTOEvent crearEventoDTO) throws Exception {
        String eventId = eventService.createEvent(crearEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Evento creado exitosamente con ID: " + eventId));
    }

    // Editar un evento
    @PutMapping("/editar-evento")
    public ResponseEntity<MessageDTO<String>> editEvent(@Valid @RequestBody editDTOEvent editarEventoDTO) throws Exception {
        String message = eventService.editEvent(editarEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, message));
    }

    // Eliminar un evento
    @DeleteMapping("/eliminar-evento/{id}")
    public ResponseEntity<MessageDTO<String>> deleteEvent(@PathVariable String id) throws Exception {
        String message = eventService.deleteEvent(id);
        return ResponseEntity.ok(new MessageDTO<>(false, message));
    }

    // Calcular el total de un evento
    @GetMapping("/calcular-total/{idEvent}")
    public ResponseEntity<MessageDTO<Double>> calculateTotal(@PathVariable String idEvent) throws Exception {
        double total = eventService.calculateTotal(idEvent);
        return ResponseEntity.ok(new MessageDTO<>(false, total));
    }


    // Coupon

    // Crear un nuevo cupón
    @PostMapping("/crear-cupon")
    public ResponseEntity<MessageDTO<String>> createCoupon(@Valid @RequestBody CouponDTO couponDTO) throws Exception {
        String couponId = couponService.createCoupon(couponDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón creado exitosamente con ID: " + couponId));
    }

    // Aplicar un cupón a un pedido
    @PostMapping("/aplicar-cupon")
    public ResponseEntity<MessageDTO<Double>> applyCoupon(@RequestParam String code, @RequestParam String orderId) throws Exception {
        double discount = couponService.applyCoupon(code, orderId);
        return ResponseEntity.ok(new MessageDTO<>(false, discount));
    }

    // Desactivar o eliminar un cupón por su ID
    @DeleteMapping("/desactivar/{couponId}")
    public ResponseEntity<MessageDTO<String>> deactivateCoupon(@PathVariable String couponId) throws Exception {
        couponService.deactivateCoupon(couponId);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón desactivado exitosamente."));
    }

    // Obtener todos los cupones disponibles (activos)
    @GetMapping("/disponibles")
    public ResponseEntity<MessageDTO<List<Coupon>>> getAvailableCoupons() {
        List<Coupon> coupons = couponService.getAvailableCoupons();
        return ResponseEntity.ok(new MessageDTO<>(false, coupons));
    }

    // Actualizar información de un cupón
    @PutMapping("/update/{couponId}")
    public ResponseEntity<MessageDTO<String>> updateCoupon(@PathVariable String couponId, @Valid @RequestBody CouponDTO couponDTO) throws Exception {
        couponService.updateCoupon(couponId, couponDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón actualizado exitosamente."));
    }


}
