package co.edu.uniquindio.proyecto.controller;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountInformation;
import co.edu.uniquindio.proyecto.dto.Account.dtoAccountItem;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.Event.createDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.editDTOEvent;
import co.edu.uniquindio.proyecto.dto.Event.eventosDTO;
import co.edu.uniquindio.proyecto.dto.JWT.MessageDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.service.Implementation.ReportService;
import co.edu.uniquindio.proyecto.service.Interfaces.AccountService;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/administrador")
public class AdminController {

    private final AccountService accountService;
    private final EventService eventService;
    private final CouponService couponService;
    private final OrderService orderService;
    private final ReportService reportService;

    // Account

    @GetMapping("/listar-cuentas")
    public ResponseEntity<MessageDTO<List<dtoAccountItem>>> listAccounts() {
        List<dtoAccountItem> accounts = accountService.listAccounts();
        return ResponseEntity.ok(new MessageDTO<>(false, accounts));
    }

    // Events

    @PostMapping("/crear-evento")
    public ResponseEntity<MessageDTO<String>> createEvent(@Valid @RequestBody createDTOEvent crearEventoDTO) throws Exception {
        String eventId = eventService.createEvent(crearEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Evento creado exitosamente con ID: " + eventId));
    }

    @PutMapping("/editar-evento")
    public ResponseEntity<MessageDTO<String>> editEvent(@Valid @RequestBody editDTOEvent editarEventoDTO) throws Exception {
        String message = eventService.editEvent(editarEventoDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, message));
    }

    @DeleteMapping("/eliminar-evento/{id}")
    public ResponseEntity<MessageDTO<String>> deleteEvent(@PathVariable String id) throws Exception {
        String message = eventService.deleteEvent(id);
        return ResponseEntity.ok(new MessageDTO<>(false, message));
    }

    @GetMapping("/obtener-eventos")
    public ResponseEntity<MessageDTO<List<eventosDTO>>> listEventAdmin() throws Exception {
        List<eventosDTO> events = eventService.allEvents();
        return ResponseEntity.ok(new MessageDTO<>(false,events));
    }


    // Resvisar este servicio
    @GetMapping("/calcular-total/{idEvent}")
    public ResponseEntity<MessageDTO<Double>> calculateTotal(@PathVariable String idEvent) throws Exception {
        double total = eventService.calculateTotal(idEvent);
        return ResponseEntity.ok(new MessageDTO<>(false, total));
    }

    //Coupon

    // Crear un nuevo cupón
    @PostMapping("/crear-cupon")
    public ResponseEntity<MessageDTO<String>> createCoupon(@Valid @RequestBody CouponDTO couponDTO) throws Exception {
        String couponId = couponService.createCoupon(couponDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón creado exitosamente con ID: " + couponId));
    }

    // Desactivar o eliminar un cupón por su ID
    @DeleteMapping("/desactivar-cupon/{couponId}")
    public ResponseEntity<MessageDTO<String>> deactivateCoupon(@PathVariable String couponId) throws Exception {
        couponService.deactivateCoupon(couponId);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón desactivado exitosamente."));
    }

    // Obtener todos los cupones disponibles (activos)
    @GetMapping("/cupones/disponibles")
    public ResponseEntity<MessageDTO<List<Coupon>>> getAvailableCoupons() {
        List<Coupon> coupons = couponService.getAvailableCoupons();
        return ResponseEntity.ok(new MessageDTO<>(false, coupons));
    }

    // Actualizar información de un cupón
    @PutMapping("/actualizar/{couponId}")
    public ResponseEntity<MessageDTO<String>> updateCoupon(@PathVariable String couponId, @Valid @RequestBody CouponDTO couponDTO) throws Exception {
        couponService.updateCoupon(couponId, couponDTO);
        return ResponseEntity.ok(new MessageDTO<>(false, "Cupón actualizado exitosamente."));
    }

    @PostMapping("/cupon/activar-cupon/{id}")
    public ResponseEntity<MessageDTO<String>> activateCoupon(@PathVariable String id) throws Exception {
        couponService.activateCoupon(id);
        return ResponseEntity.ok(new MessageDTO<>(true, "Se activo el cupon satisfactoriamente"));
    }


    @GetMapping("/filter")
    public List<Order> filterOrdersByPaymentState(@RequestParam(required = false) PaymentState state) throws Exception {
        // Crear el DTO con los parámetros recibidos
        dtoOrderFilter filter1 = new dtoOrderFilter(state);

        // Llamar al servicio con el DTO y retornar los resultados
        return orderService.paymentFilterByState(filter1);
    }

    // Reporte
    @PostMapping("/reporte")
    public ResponseEntity<MessageDTO<String>> generateSalesReportPDF() throws Exception{
        reportService.generateSalesReportPDF();
        return ResponseEntity.ok(new MessageDTO<>(true, "Reporte generado exitosamente. (revisa tu escritorio)"));
    }


}
