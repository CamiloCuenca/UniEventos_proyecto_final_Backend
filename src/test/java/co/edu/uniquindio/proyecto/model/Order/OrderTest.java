package co.edu.uniquindio.proyecto.model.Order;

import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.PaymentDTO;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderTest() throws Exception {

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .idEvent(new ObjectId("66f5c5a0de22e82833106d92"))
                .price(13.000)
                .localityName("General")
                .amount(200)
                .build();

        orderDetails.add(orderDetail1);

        PaymentDTO paymentDTO = new PaymentDTO(
                "COP",
                PaymentType.CREDIT_CARD,   // Usar Enum para tipo de pago
                "123",
                LocalDateTime.now(),
                13.000,
                PaymentState.COMPLETED     // Usar Enum para estado
        );

        OrderDTO orderDTO = new OrderDTO(
                "66f8db70c1ce3939dbcbe1e0",
                LocalDateTime.now(),
                "12345",
                orderDetails,
                paymentDTO,
                13.000,
                null
        );

        orderService.createOrder(orderDTO);
    }

    @Test
    public void updateOrderTest() throws Exception {
        String orderId = "66faf1817118613d158799f1";

        // Crear detalles de la orden
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .idEvent(new ObjectId("66f5c5a0de22e82833106d93"))
                .price(13.000)
                .localityName("General")
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


}
