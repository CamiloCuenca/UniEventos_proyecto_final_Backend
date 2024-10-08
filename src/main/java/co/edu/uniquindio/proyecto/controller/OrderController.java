package co.edu.uniquindio.proyecto.controller;

import co.edu.uniquindio.proyecto.Enum.EventType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.dto.Event.dtoEventFilter;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
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

    @PostMapping("/realizar-pago")
    public ResponseEntity<MessageDTO<Preference>> realizarPagoProfe(@RequestParam("idOrden") String idOrden) throws Exception{
        return ResponseEntity.ok().body(new MessageDTO<>(false, orderService.realizarPago(idOrden)));
    }

    @PostMapping("/notificacion-pago")
    public void recibirNotificacionMercadoPago(@RequestBody Map<String, Object> requestBody) {
        orderService.recibirNotificacionMercadoPago(requestBody);
    }


}
