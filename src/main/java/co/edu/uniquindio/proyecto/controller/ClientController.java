package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.changePasswordDTO;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Account.editAccountDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
public class ClientController {

    private final AccountService accountService;
    private final EventService eventService;
    private final CouponService couponService;

    // Account

    @GetMapping("/obtener-info/{id}")
    public dtoAccountInformation obtainAccountInformation(@PathVariable String id) throws Exception {
        return accountService.obtainAccountInformation(id);
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

    // Event

    // Coupon

    // Aplicar un cupón a un pedido
    @PostMapping("/aplicar-cupon")
    public ResponseEntity<MessageDTO<Double>> applyCoupon(@RequestParam String code, @RequestParam String orderId) throws Exception {
        double discount = couponService.applyCoupon(code, orderId);
        return ResponseEntity.ok(new MessageDTO<>(false, discount));
    }





}
