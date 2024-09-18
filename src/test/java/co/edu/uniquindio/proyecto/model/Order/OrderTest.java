package co.edu.uniquindio.proyecto.model.Order;

import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Payment;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

@SpringBootTest
public class OrderTest {

    @Autowired
    private OrderService orderService;



    @Test
    public void createOrderTest() throws Exception {

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail1 = OrderDetail.builder()
                .idEvent(new ObjectId("66dcf9d99b293d0c2aba1372"))
                .price(13.000)
                .localityName("General")
                .EventName("Jazz Night")
                .amount(200)
                .build();

        orderDetails.add(orderDetail1);

        Payment payment = Payment.builder()
                .currency("COP")
                .typePayment("No se")
                .authorizationCode("123")
                .date(LocalDateTime.now())
                .transactionValue(13.000)
                .state("Pagado")
                .build();

        Order order= Order.builder()
                .id(String.valueOf(new ObjectId("66a2c14dd9219911cd34f2c0")))
                .date(LocalDateTime.now())
                .gatewayCode("12345")
                .items(orderDetails)
                .payment(payment)
                .total(13.000)
               // .idCoupon()
                .build();

        orderService.createOrder(order);
    }
}
