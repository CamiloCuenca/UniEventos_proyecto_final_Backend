package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.Event.createDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.editDTOEvent;
import co.edu.uniquindio.proyecto.dto.JWT.TokenDTO;
import co.edu.uniquindio.proyecto.dto.JWT.dtoMessage;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
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
    public ResponseEntity<dtoMessage<String>> crearCuenta(@Valid @RequestBody createAccountDTO cuenta) throws Exception{
        accountService.createAccount(cuenta);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cuenta creada exitosamente"));
    }

    @PutMapping("/editar-perfil/{id}")
    public ResponseEntity<dtoMessage<String>> editAccount(@Valid @RequestBody editAccountDTO cuenta ,@PathVariable String id) throws Exception{
        accountService.editAccount(cuenta,id);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cuenta editada exitosamente"));

    }
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<dtoMessage<String>> deleteAccount(@PathVariable String id) throws Exception{
        accountService.deleteAccount(id);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cuenta eliminada exitosamente"));
    }


    @GetMapping("/listar-cuentas")
    public ResponseEntity<dtoMessage<List<dtoAccountItem>>> listAccounts() {
        List<dtoAccountItem> accounts = accountService.listAccounts();
        return ResponseEntity.ok(new dtoMessage<>(false, accounts));
    }


    @PostMapping("/enviar-codigo/{correo}")
    public ResponseEntity<dtoMessage<String>>  sendPasswordRecoveryCode(@PathVariable String correo) throws Exception{
        accountService.sendPasswordRecoveryCode(correo);
        return ResponseEntity.ok(new dtoMessage<>(true, "Se envio el codigo exitosamente"));
    }

    @PutMapping("/cambiar-contrsena/{correo}")
    public ResponseEntity<dtoMessage<String>> changePassword(@Valid @RequestBody changePasswordDTO changePasswordDTO,@PathVariable String correo) throws Exception{
        accountService.changePassword(changePasswordDTO, correo);
        return ResponseEntity.ok(new dtoMessage<>(true, "Se cambio la contraseña exitosamente"));
    }


    @PostMapping("/iniciar-sesion")
    public ResponseEntity<dtoMessage<TokenDTO>> iniciarSesion(@Valid @RequestBody LoginDTO loginDTO) throws Exception{
        TokenDTO token = accountService.login(loginDTO);
        return ResponseEntity.ok(new dtoMessage<>(false, token));
    }

    @PostMapping("/activar-cuenta")
    public ResponseEntity<dtoMessage<String>> activateAccount(@Valid @RequestBody String correo , @Valid @RequestBody String code) throws Exception{
        accountService.activateAccount(correo, code);
        return ResponseEntity.ok(new dtoMessage<>(true, "Se cuenta se activo exitosamente"));
    }


    // Event

    // Crear un evento
    @PostMapping("/crear-evento")
    public ResponseEntity<dtoMessage<String>> createEvent(@Valid @RequestBody createDTOEvent crearEventoDTO) throws Exception {
        String eventId = eventService.createEvent(crearEventoDTO);
        return ResponseEntity.ok(new dtoMessage<>(false, "Evento creado exitosamente con ID: " + eventId));
    }

    // Editar un evento
    @PutMapping("/editar-evento")
    public ResponseEntity<dtoMessage<String>> editEvent(@Valid @RequestBody editDTOEvent editarEventoDTO) throws Exception {
        String message = eventService.editEvent(editarEventoDTO);
        return ResponseEntity.ok(new dtoMessage<>(false, message));
    }

    // Eliminar un evento
    @DeleteMapping("/eliminar-evento/{id}")
    public ResponseEntity<dtoMessage<String>> deleteEvent(@PathVariable String id) throws Exception {
        String message = eventService.deleteEvent(id);
        return ResponseEntity.ok(new dtoMessage<>(false, message));
    }

    // Calcular el total de un evento
    @GetMapping("/calcular-total/{idEvent}")
    public ResponseEntity<dtoMessage<Double>> calculateTotal(@PathVariable String idEvent) throws Exception {
        double total = eventService.calculateTotal(idEvent);
        return ResponseEntity.ok(new dtoMessage<>(false, total));
    }


    // Coupon

    // Crear un nuevo cupón
    @PostMapping("/crear-cupon")
    public ResponseEntity<dtoMessage<String>> createCoupon(@Valid @RequestBody CouponDTO couponDTO) throws Exception {
        String couponId = couponService.createCoupon(couponDTO);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cupón creado exitosamente con ID: " + couponId));
    }

    // Aplicar un cupón a un pedido
    @PostMapping("/aplicar-cupon")
    public ResponseEntity<dtoMessage<Double>> applyCoupon(@RequestParam String code, @RequestParam String orderId) throws Exception {
        double discount = couponService.applyCoupon(code, orderId);
        return ResponseEntity.ok(new dtoMessage<>(false, discount));
    }

    // Desactivar o eliminar un cupón por su ID
    @DeleteMapping("/desactivar/{couponId}")
    public ResponseEntity<dtoMessage<String>> deactivateCoupon(@PathVariable String couponId) throws Exception {
        couponService.deactivateCoupon(couponId);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cupón desactivado exitosamente."));
    }

    // Obtener todos los cupones disponibles (activos)
    @GetMapping("/disponibles")
    public ResponseEntity<dtoMessage<List<Coupon>>> getAvailableCoupons() {
        List<Coupon> coupons = couponService.getAvailableCoupons();
        return ResponseEntity.ok(new dtoMessage<>(false, coupons));
    }

    // Actualizar información de un cupón
    @PutMapping("/update/{couponId}")
    public ResponseEntity<dtoMessage<String>> updateCoupon(@PathVariable String couponId, @Valid @RequestBody CouponDTO couponDTO) throws Exception {
        couponService.updateCoupon(couponId, couponDTO);
        return ResponseEntity.ok(new dtoMessage<>(false, "Cupón actualizado exitosamente."));
    }


}
