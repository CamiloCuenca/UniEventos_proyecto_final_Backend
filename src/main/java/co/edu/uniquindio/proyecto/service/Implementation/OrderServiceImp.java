package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.repository.CouponRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import co.edu.uniquindio.proyecto.service.Interfaces.EmailService;
import co.edu.uniquindio.proyecto.service.Interfaces.ImagesService;
import co.edu.uniquindio.proyecto.service.Interfaces.OrderService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository cuentaRepo;
    private final AccountServiceimp accountServiceimp;
    private final CouponService couponService;
    private final EventRepository eventRepository;
    private final CouponRepository  couponRepository;
    private final CouponServiceImp couponServiceImp;
    private final EmailService emailService;
    private final QRCodeService qrCodeService;
    private final ImagesService imagesService;



    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        if (orderDTO == null) {
            throw new Exception("No se puede crear una orden");
        }

        // Convertir el DTO a una entidad Order
        Order order = Order.builder()
                .id(ObjectId.get().toString())  // Generar ID automáticamente
                .idAccount(new ObjectId(orderDTO.idAccount()))
                .date(orderDTO.date())
                .gatewayCode(orderDTO.gatewayCode())
                .items(orderDTO.items())  // Los detalles de la orden vienen directamente desde el DTO
                .payment(orderDTO.payment().toEntity())  // Convertir el PaymentDTO a Payment
                .total(orderDTO.total())
                .codeCoupon(orderDTO.CodeCoupon() != null ? orderDTO.CodeCoupon() : null)  // Manejar el ID del cupón opcionalmente
                .build();

        // Verificar si la cuenta existe
        if (cuentaRepo.findByIdnumber(orderDTO.idAccount()).isEmpty()) {
            throw new Exception("El id de la cuenta no existe");
        }

        // Verificar si el evento existe
        if (eventRepository.findById(String.valueOf(orderDTO.items().get(0).getIdEvent())).isEmpty()) {
            throw new Exception("El ID del evento no existe");
        }

        // Verificar si es la primera orden de la cuenta
        List<Order> ordersByAccount = orderRepository.findByAccountId(orderDTO.idAccount());
        if (ordersByAccount.isEmpty()) {
            // Esta es la primera orden para la cuenta
            System.out.println("¡Esta es la primera orden para la cuenta: " + orderDTO.idAccount() + "!");
            Optional<Account> optionalAccount = cuentaRepo.findById(orderDTO.idAccount());
            sendCupon(optionalAccount.get().getEmail());

        }

        // Guardar la orden en la base de datos
        Order savedOrder = orderRepository.save(order);

        // Aplicar cupón si está presente
        if (orderDTO.CodeCoupon() != null) {
            // Aquí puedes invocar el servicio de aplicar el cupón
            couponServiceImp.applyCoupon(orderDTO.CodeCoupon(), savedOrder.getId());
        }

        // Generar y enviar el codigo QR
        // Generar el QR en base64
        String qrBase64 = qrCodeService.generateQRCode(order.getId());

        // Convertir la base64 a byte array
        byte[] qrBytes = Base64.getDecoder().decode(qrBase64);

        // Subir el QR como imagen a Firebase
        String qrUrl = imagesService.uploadQR(qrBytes, "order-" + order.getId() + "-qr.png");

        // Enviar el QR por email
        Optional<Account> account = cuentaRepo.findEmailById(String.valueOf(order.getIdAccount()));
        if (account.isPresent()) {
            String email = account.get().getEmail();
            emailService.sendQrByEmail(email, qrUrl);
        } else {
            // Manejo de caso en que no se encuentre la cuenta
            throw new Exception("El email no existe");
        }


        return savedOrder;
    }

    private void sendCupon(String email) throws Exception {
        CouponDTO couponDTO = new CouponDTO(
                "Cupón de Bienvenida",              // Nombre del cupón
               accountServiceimp.generateRandomCouponCode(),         // Código único de cupón
                "15",                               // Descuento del 15%
                LocalDateTime.now().plusDays(30),   // Fecha de expiración (30 días a partir de ahora)
                CouponStatus.AVAILABLE,             // Estado disponible
                TypeCoupon.ONLY,                     // Tipo de cupón: uso único
                null,                                 // ID del evento: (en este caso no es requerido)
                LocalDateTime.now()                 // Fecha de inicio: hoy
        );


        // Crear el cupón usando el servicio de cupones
        String couponId = couponService.createCoupon(couponDTO);

        // Preparar el cuerpo del correo que incluye el código del cupón
        String plainTextMessage = "Estimado usuario,\n\n" +
                "Gracias por realizar su primera compra en nuestra plataforma. Para celebrar su compra, le ofrecemos un cupón con un 10% de descuento en su próxima compra:\n\n" +
                "Código de cupón: " + couponDTO.code() + "\n\n" +
                "Este cupón es válido por 30 días y solo puede ser utilizado una vez.\n\n" +
                "Si tiene alguna duda, por favor contáctenos.\n\n" +
                "Atentamente,\n" +
                "El equipo de UniEventos";

        // Enviar el correo con el código de cupón

        emailService.sendMail(new EmailDTO(email, "\"Cupón de Bienvenida\"", plainTextMessage));
    }

    @Override
    public Order updateOrder(String orderId, OrderDTO updatedOrderDTO) throws Exception {
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isEmpty()) {
            throw new Exception("No se puede actualizar una orden");
        }

        Order order = existingOrder.get();
        order.setTotal(updatedOrderDTO.total());
        order.setItems(updatedOrderDTO.items());
        order.setPayment(updatedOrderDTO.payment().toEntity());  // Convertir el PaymentDTO a Payment

        // Aplicar el cupón si es que se proporcionó un nuevo cupón
        if (updatedOrderDTO.CodeCoupon() != null) {
            couponServiceImp.applyCoupon(updatedOrderDTO.CodeCoupon(), order.getId());
        }

        return orderRepository.save(order);
    }



    @Override
    public void deleteOrder(String orderId) throws Exception {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new Exception("No se puede eliminar la orden");
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getOrderById(String orderId) throws Exception {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new Exception("La orden no existe"));
    }

    @Override
    public List<Order> getOrdersByUser(String accountId) throws Exception {
        return orderRepository.findByAccountId(accountId);
    }

    @Override
    public List<Order> getAllOrders() throws Exception {
        return orderRepository.findAll();
    }
}
