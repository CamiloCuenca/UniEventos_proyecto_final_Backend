package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.dto.Account.*;
import co.edu.uniquindio.proyecto.dto.Carts.CartDetailDTO;
import co.edu.uniquindio.proyecto.dto.Carts.CartItemSummaryDTO;
import co.edu.uniquindio.proyecto.dto.Carts.UpdateCartItemDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.exception.Cart.CartNotFoundException;
import co.edu.uniquindio.proyecto.exception.account.InvalidValidationCodeException;
import co.edu.uniquindio.proyecto.exception.account.PasswordsDoNotMatchException;
import co.edu.uniquindio.proyecto.exception.account.ValidationCodeExpiredException;
import co.edu.uniquindio.proyecto.model.Carts.CartDetail;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.service.Interfaces.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
public class ClientController {

    private final AccountService accountService;
    private final CouponService couponService;
    private final CartService cartService;
    private final OrderService orderService;


    @GetMapping("/cuenta/obtener-info/{id}")
    public ResponseEntity<MessageDTOC<dtoAccountInformation>> obtainAccountInformation(@PathVariable String id) throws Exception {
        // Llamamos al servicio para obtener la información de la cuenta
        MessageDTOC<dtoAccountInformation> response = accountService.obtainAccountInformation(id);
        // Si no hay error, retornamos la información de la cuenta
        return ResponseEntity.ok(response);
    }

    @PutMapping("cuenta/editar-perfil/{id}")
    public ResponseEntity<MessageDTO<String>> editEvent(@Valid @RequestBody editAccountDTO editarAccount, @PathVariable String id) throws Exception {
        String message = accountService.editAccount(editarAccount, id);
        return ResponseEntity.ok(new MessageDTO<>(false, message));
    }

    @PutMapping("/cuenta/editar-password/{id}")
    public ResponseEntity<MessageDTO<String>> updatePassword(@Valid @RequestBody updatePasswordDTO cuenta, @PathVariable String id) throws Exception {
        accountService.updatePassword(cuenta, id);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta editada exitosamente"));
    }

    @PostMapping("/cuenta/eliminar/{id}")
    public ResponseEntity<MessageDTO<String>> deleteAccount(@PathVariable String id, @Valid @RequestBody PasswordDTO password) throws Exception {
        accountService.deleteAccount(id, password);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cuenta eliminada exitosamente"));
    }

    @PostMapping("/email/enviar-codigo/{correo}")
    public ResponseEntity<MessageDTO<String>> sendPasswordRecoveryCode(@PathVariable String correo) throws Exception {
        accountService.sendPasswordRecoveryCode(correo);
        return ResponseEntity.ok(new MessageDTO<>(true, "Se envio el codigo exitosamente"));
    }

    @PostMapping("/email/enviar-codigoActive/{correo}")
    public ResponseEntity<MessageDTO<String>> sendActiveCode(@PathVariable String correo) throws Exception {
        accountService.sendActiveCode(correo);
        return ResponseEntity.ok(new MessageDTO<>(true, "Se envio el codigo exitosamente"));
    }


    // Endpoint para cambiar la contraseña
    @PutMapping("/cuenta/cambiar-password")
    public ResponseEntity<MessageDTO<String>> cambiarPassword(@Valid @RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            String message = accountService.changePassword(changePasswordDTO);
            return ResponseEntity.ok(new MessageDTO<>(false, message));
        } catch (InvalidValidationCodeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDTO<>(true, "No se encontró la cuenta o el código de validación es inválido."));
        } catch (ValidationCodeExpiredException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO<>(true, "El código de recuperación ha expirado o no es válido."));
        } catch (PasswordsDoNotMatchException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageDTO<>(true, "Las contraseñas no coinciden."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageDTO<>(true, "Ocurrió un error inesperado. Intente nuevamente."));
        }
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
    public ResponseEntity<MessageDTO<String>> removeItemFromCart(@PathVariable String accountId, @PathVariable String eventId) throws Exception {
        try {
            cartService.removeItemFromCart(accountId, eventId);
            return ResponseEntity.ok(new MessageDTO<>(false, "!se retiro correctamente el item!"));
        } catch (CartNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageDTO<>(true, e.getMessage()));
        }
    }

    @GetMapping("/carrito/obtener-items-carrito/{accountId}")
    public ResponseEntity<MessageDTO<List<CartDetail>>> getCartItems(@PathVariable String accountId) throws Exception {
        List<CartDetail> items = cartService.getCartItems(accountId);
        return ResponseEntity.ok().body(new MessageDTO<>(false, items));
    }

    @GetMapping("/carrito/resumen-items/{accountId}")
    public ResponseEntity<MessageDTO<List<CartItemSummaryDTO>>> getCartItemSummary(@PathVariable String accountId) throws Exception {
        List<CartItemSummaryDTO> items = cartService.getCartItemSummary(accountId);
        return ResponseEntity.ok().body(new MessageDTO<>(false, items));
    }


    @PostMapping("/realizar-pago/{idOrden}")
    public ResponseEntity<MessageDTO<String>> realizarPago(@PathVariable String idOrden) throws Exception {
        orderService.realizarPago(idOrden);
        return ResponseEntity.ok(new MessageDTO<>(false, "pago exitoso"));
    }
    // Coupon

    // Aplicar un cupón a un pedido
    @PostMapping("/cupon/aplicar-cupon")
    public ResponseEntity<MessageDTO<Double>> applyCoupon(@RequestParam String code, @RequestParam String orderId) throws Exception {
        double discount = couponService.applyCoupon(code, orderId);
        return ResponseEntity.ok(new MessageDTO<>(false, discount));
    }


    //Order

    @GetMapping("/filter")
    public List<Order> filterOrdersByPaymentState(@RequestParam(required = false) PaymentState state) throws Exception {
        // Crear el DTO con los parámetros recibidos
        dtoOrderFilter filter1 = new dtoOrderFilter(state);

        // Llamar al servicio con el DTO y retornar los resultados
        return orderService.paymentFilterByState(filter1);
    }


}
