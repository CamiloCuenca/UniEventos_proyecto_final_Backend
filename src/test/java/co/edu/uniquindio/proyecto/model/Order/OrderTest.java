package co.edu.uniquindio.proyecto.model.Order;

import co.edu.uniquindio.proyecto.Enum.Localities;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.PaymentDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.EventService;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import co.edu.uniquindio.proyecto.Enum.PaymentType;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    EventRepository eventRepository;


    @Test
    public void createOrderTest() throws Exception {
        // Obtener el evento desde la base de datos para asegurar que los datos estén actualizados
        ObjectId eventId = new ObjectId("66f5c5a0de22e82833106d92");

        // Buscar el evento por su ID en la base de datos
        Optional<Event> eventOptional = eventRepository.findById(eventId.toHexString());

        // Verificar que el evento exista antes de continuar
        assertTrue(eventOptional.isPresent(), "El evento no se encontró en la base de datos");
        Event event = eventOptional.get();

        List<OrderDetail> orderDetails = new ArrayList<>();

        // Obtener el precio de la localidad desde el evento
        Locality locality = event.getLocalities().stream()
                .filter(loc -> loc.getName().equals(Localities.GENERAL))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Localidad no encontrada"));

        // Crear detalles de la orden utilizando el precio de la localidad obtenida del evento
        OrderDetail orderDetail1 = OrderDetail.builder()
                .idEvent(eventId)
                .price(locality.getPrice()) // Usar el precio obtenido del evento
                .localityName(Localities.GENERAL)
                .amount(2)
                .build();

        orderDetails.add(orderDetail1);

        // Calcular el total basado en los detalles de la orden
        double total = orderDetails.stream()
                .mapToDouble(detail -> detail.getPrice() * detail.getAmount())
                .sum();

        PaymentDTO paymentDTO = new PaymentDTO(
                "COP",
                PaymentType.CREDIT_CARD,
                "",
                LocalDateTime.now(),
                total,
                PaymentState.in_process
        );

        OrderDTO orderDTO = new OrderDTO(
                "66f8db70c1ce3939dbcbe1e0",
                LocalDateTime.now(),
                "",
                orderDetails,
                paymentDTO,
                total,  // Usar el total calculado
                null
        );

        // Ejecutar la creación de la orden
        Order savedOrder = orderService.createOrder(orderDTO);

        // Verificar que el total guardado es el esperado
        assertEquals(total, savedOrder.getTotal(), 0.01); // Verifica que el total guardado sea correcto
    }


    @Test
    public void updateOrderTest() throws Exception {
        String orderId = "66faf1817118613d158799f1";

        // Crear detalles de la orden
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .idEvent(new ObjectId("66f5c5a0de22e82833106d93"))
                .price(13.000)
                .localityName(Localities.GENERAL)
                .amount(200)
                .build();

        orderDetails.add(orderDetail1);

        // Crear el pago asociado a la orden
        PaymentDTO paymentDTO = new PaymentDTO(
                "COP",
                PaymentType.CREDIT_CARD,   // Usar Enum para tipo de pago
                "123",
                LocalDateTime.now(),
                135.000,                  // Cambiar el valor de la transacción
                PaymentState.COMPLETED     // Usar Enum para estado
        );

        // Crear un OrderDTO con los nuevos valores que queremos actualizar
        OrderDTO updatedOrderDTO = new OrderDTO(
                "66f8db70c1ce3939dbcbe1e0",  // ID del usuario
                LocalDateTime.now(),
                "12345",  // Nuevo código de pasarela de pago
                orderDetails,
                paymentDTO,
                135.000,   // Nuevo total de la orden
                null       // Cupón (en este caso, nulo)
        );

        // Llamar al servicio para actualizar la orden
        Order updatedOrder = orderService.updateOrder(orderId, updatedOrderDTO);

        // Verificar que los cambios fueron realizados correctamente
        assertNotNull(updatedOrder);
        assertEquals(updatedOrderDTO.total(), updatedOrder.getTotal());
        assertEquals(updatedOrderDTO.items().size(), updatedOrder.getItems().size());
        assertEquals(updatedOrderDTO.payment().transactionValue(), updatedOrder.getPayment().getTransactionValue());
    }

    @Test
    public void deleteOrderTest() throws Exception {
        String orderId = "67020240653239785780116c";
        orderService.deleteOrder(orderId);
    }

    @Test
    public void getOrdersByUserTest() throws Exception {
        String accountId = "66f8db70c1ce3939dbcbe1e0";
        System.out.println(orderService.getOrdersByUser(accountId));
    }

    @Test
    public void getAllOrdersTest() throws Exception {
        System.out.println(orderService.getAllOrders());
    }

    @Test
    public void obtenerOrdenTest() throws Exception {
        String idOrden = "67020240653239785780116d";
        System.out.println(orderService.obtenerOrden(idOrden).toString());
    }

    @Test
    public void paymentFilterByStateTest() throws Exception {
        dtoOrderFilter filter = new dtoOrderFilter(PaymentState.in_process);
        System.out.println(orderService.paymentFilterByState(filter));
    }

    @Test
    public void testPaymentFilterByStateCliente() throws Exception {
        // Arrange
        dtoOrderFilter filter = new dtoOrderFilter(PaymentState.in_process); // Crea tu filtro aquí
        String idAccount = "66f8db70c1ce3939dbcbe1e0"; // ID de cuenta de prueba

        // Act
        List<Order> result = orderService.paymentFilterByStateCliente(filter, idAccount);

        System.out.println(result);
        // Assert
        assertNotNull(result);

    }



}
