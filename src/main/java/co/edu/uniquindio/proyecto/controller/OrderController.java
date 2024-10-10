package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.dto.Event.dtoEventFilter;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Pago;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orden")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/crear-orden")
    public ResponseEntity<MessageDTO<String>> createOrder(@RequestBody OrderDTO order)throws Exception {
        orderService.createOrder(order);
        return ResponseEntity.ok().body(new MessageDTO<>(false," Se creo la orden de compra de manera exitosa"));
    }

    @PutMapping ("/actualizar-orden/{orderId}")
    public ResponseEntity<MessageDTO<String>> updateOrder(@RequestBody OrderDTO order, @PathVariable String orderId)throws Exception {
        orderService.updateOrder(orderId, order);
        return ResponseEntity.ok().body(new MessageDTO<>(false,"Se actualizo la orden de manera satisfactoria"));
    }

    @DeleteMapping("/eliminar-orden/{orderId}")
    public ResponseEntity<MessageDTO<String>> deleteOrder(@PathVariable String orderId)throws Exception {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body(new MessageDTO<>(false,"Se elimino la orden de manera satisfactoria"));
    }


    @GetMapping("/obtener-orden-usuario/{accountId}")
    public ResponseEntity<MessageDTO<List<Order>>> obtenerOrdenUsuario( @PathVariable String accountId) throws Exception {
        List<Order> orders = orderService.getOrdersByUser(accountId);
        return ResponseEntity.ok().body(new MessageDTO<>(false,orders));
    }

    @GetMapping("/obtener-ordenes")
    public ResponseEntity<MessageDTO<List<Order>>> obtenerOrdenes() throws Exception {
        List<Order> orders =orderService.getAllOrders();
        return ResponseEntity.ok().body(new MessageDTO<>(false,orders));
    }

    @GetMapping("/obtener-orden/{orderId}")
    public ResponseEntity<MessageDTO<Order>> obtenerOrden(@PathVariable String orderId) throws Exception {
        Order order = orderService.obtenerOrden(orderId);
        return ResponseEntity.ok().body(new MessageDTO<>(false,order));
    }

    @PostMapping("/realizar-pago")
    public ResponseEntity<MessageDTO<Preference>> realizarPagoProfe(@RequestParam("idOrden") String idOrden) throws Exception{
        return ResponseEntity.ok().body(new MessageDTO<>(false, orderService.realizarPago(idOrden)));
    }

    @PostMapping("/notificacion-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> requestBody) {
        orderService.recibirNotificacionMercadoPago(requestBody);
    }


}
