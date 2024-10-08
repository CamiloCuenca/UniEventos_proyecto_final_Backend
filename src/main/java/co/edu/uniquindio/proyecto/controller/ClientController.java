package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.dto.Account.changePasswordDTO;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Account.editAccountDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.service.Interfaces.*;
import com.mercadopago.resources.preference.Preference;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
public class ClientController {

    private final AccountService accountService;
    private final EventService eventService;
    private final CouponService couponService;
    private final CartService cartService;
    private final OrderService orderService;

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

    //carrito

    @PostMapping("/carrito/agregar-item/{accountId}")
    public ResponseEntity<MessageDTO<String>> addItemToCart(
            @Valid @RequestBody CartDetailDTO cartDetailDTO,
            @PathVariable String accountId) throws Exception {
        try {
            // Llamada al servicio para agregar el ítem al carrito
            cartService.addItemToCart(accountId, cartDetailDTO);
            // Retornar una respuesta exitosa
            return ResponseEntity.ok(new MessageDTO<>(false, "Ítem agregado exitosamente al carrito"));
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante la operación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO<>(true, "Error al agregar el ítem: " + e.getMessage()));
        }
    }
    @PostMapping("/carrito/actualizar-item/{accountId}/{itemId}")
    public ResponseEntity<MessageDTO<String>> updateCartItem(
            @PathVariable String accountId,
            @PathVariable String itemId,
            @Valid @RequestBody UpdateCartItemDTO updateCartItemDTO) {
        try {
            // Llamada al servicio para actualizar el ítem en el carrito
            cartService.updateCartItem(accountId, itemId, updateCartItemDTO);
            // Retornar una respuesta exitosa
            return ResponseEntity.ok(new MessageDTO<>(false, "Ítem actualizado exitosamente en el carrito"));
        } catch (Exception e) {
            // Manejar cualquier excepción que ocurra durante la operación
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO<>(true, "Error al actualizar el ítem: " + e.getMessage()));
        }
    }
    @PostMapping("/carrito/limpiar-carrito/{accountId}")
    public ResponseEntity<MessageDTO<String>> clearCart(@PathVariable String accountId) throws Exception {
        try {
            cartService.clearCart(accountId);
            return ResponseEntity.ok(new MessageDTO<>(true, "!Se limpió correctamente el carro!"));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDTO<>(false, e.getMessage()));
        }
    }
    @PostMapping("/carrito/eliminar-item/{accountId}/{eventId}")
    public ResponseEntity<MessageDTO<String>> removeItemFromCart(@PathVariable String accountId,@PathVariable String eventId) throws Exception {
        try {
            cartService.removeItemFromCart(accountId, eventId);
            return ResponseEntity.ok(new MessageDTO<>(true, "!se retiro correctamente el item!"));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDTO<>(false, e.getMessage()));
        }
    }


    @PostMapping("/realizar-pago/{idOrden}")
    public ResponseEntity<MessageDTO<String>> realizarPago(@PathVariable String idOrden)throws Exception {
        orderService.realizarPago(idOrden);
        return ResponseEntity.ok(new MessageDTO<>(false,"pago exitoso"));
    }
    // Coupon

    // Aplicar un cupón a un pedido
    @PostMapping("/aplicar-cupon")
    public ResponseEntity<MessageDTO<Double>> applyCoupon(@RequestParam String code, @RequestParam String orderId) throws Exception {
        double discount = couponService.applyCoupon(code, orderId);
        return ResponseEntity.ok(new MessageDTO<>(false, discount));
    }





}
