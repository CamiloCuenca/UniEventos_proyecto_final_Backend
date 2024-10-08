package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.Enum.PaymentType;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.model.Accounts.Account;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.Events.Locality;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.OrderDetail;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Pago;
import co.edu.uniquindio.proyecto.repository.AccountRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.*;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {

    @Autowired
    MongoTemplate mongoTemplate;

    private final OrderRepository orderRepository;
    private final AccountRepository cuentaRepo;
    private final CouponService couponService;
    private final EventRepository eventRepository;
    private final CouponServiceImp couponServiceImp;
    private final EmailService emailService;
    private final QRCodeService qrCodeService;
    private final ImagesService imagesService;
    private final EventService eventService;


    /**
     * Este mètodo crea un orden de compra segun los datos de orderDTO
     *
     * @param orderDTO
     * @return Order
     * @throws Exception
     */
    @Override
    public Order createOrder(OrderDTO orderDTO) throws Exception {
        if (orderDTO == null) {
            throw new Exception("No se puede crear una orden");
        }

        // Calcular el total de la orden
        double total = 0;
        for (OrderDetail item : orderDTO.items()) {
            total += item.getPrice() * item.getAmount();
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


        // Generar el QR de la orden en base64
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
               CouponService.generateRandomCouponCode(),         // Código único de cupón
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

    @Override
    public Preference realizarPago(String idOrden) throws Exception {


        // Obtener la orden guardada en la base de datos y los ítems de la orden
        Order ordenGuardada = getOrderById(idOrden);
        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();


        // Recorrer los items de la orden y crea los ítems de la pasarela
        for(OrderDetail item : ordenGuardada.getItems()){


            // Obtener el evento y la localidad del ítem
            Event evento = eventService.obtenerEvento(item.getIdEvent().toString());
            Locality localidad = evento.obtenerLocalidad(item.getLocalityName());



            // Crear el item de la pasarela
            PreferenceItemRequest itemRequest =
                    PreferenceItemRequest.builder()
                            .id(evento.getId())
                            .title(evento.getName())
                            .pictureUrl(evento.getCoverImage())
                            .categoryId(evento.getType().name())
                            .quantity(item.getAmount())
                            .currencyId("COP")
                            .unitPrice(BigDecimal.valueOf(localidad.getPrice()))
                            .build();


            itemsPasarela.add(itemRequest);
            System.out.println("Precio unitario de la localidad: " + localidad.getPrice());

        }


        // Configurar las credenciales de MercadoPago
        MercadoPagoConfig.setAccessToken("APP_USR-1074363858207208-100622-1c36028d107a18a9507c21ceadc5069e-2021909487");


        // Configurar las urls de retorno de la pasarela (Frontend)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("URL PAGO EXITOSO")
                .failure("URL PAGO FALLIDO")
                .pending("URL PAGO PENDIENTE")
                .build();


        // Construir la preferencia de la pasarela con los ítems, metadatos y urls de retorno
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrls)
                .items(itemsPasarela)
                .metadata(Map.of("id_orden", ordenGuardada.getId()))
                .notificationUrl("https://3c0c-2800-e2-7180-1775-00-2.ngrok-free.app/api/orden/notificacion-pago")
                .build();


        // Crear la preferencia en la pasarela de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);


        // Guardar el código de la pasarela en la orden
        ordenGuardada.setCodigoPasarela( preference.getId() );
        orderRepository.save(ordenGuardada);


        return preference;
    }

    @Override
    public void recibirNotificacionMercadoPago(Map<String, Object> request) {
        try {


            // Obtener el tipo de notificación
            Object tipo = request.get("type");


            // Si la notificación es de un pago entonces obtener el pago y la orden asociada
            if ("payment".equals(tipo)) {


                // Capturamos el JSON que viene en el request y lo convertimos a un String
                String input = request.get("data").toString();


                // Extraemos los números de la cadena, es decir, el id del pago
                String idPago = input.replaceAll("\\D+", "");


                // Se crea el cliente de MercadoPago y se obtiene el pago con el id
                PaymentClient client = new PaymentClient();
                Payment payment = client.get( Long.parseLong(idPago) );


                // Obtener el id de la orden asociada al pago que viene en los metadatos
                String idOrden = payment.getMetadata().get("id_orden").toString();


                // Se obtiene la orden guardada en la base de datos y se le asigna el pago
                Order orden = obtenerOrden(idOrden);
                Pago pago = crearPago(payment);
                orden.setPayment(pago);
                orderRepository.save(orden);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order obtenerOrden(String idOrden) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(idOrden);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new Exception("Orden no encontrada con ID: " + idOrden);
        }
    }

    @Override
    public List<Order> paymentFilterByState(dtoOrderFilter filter) {
        Query query = new Query();

        // Filtrar las órdenes por el estado del pago
        if (filter.state() != null) {
            // Usamos "payment.state" para acceder al estado del pago dentro del subdocumento payment
            query.addCriteria(Criteria.where("payment.state").is(filter.state()));
        }

        // Retornar las órdenes que cumplen con el filtro
        return mongoTemplate.find(query, Order.class);
    }



    private Pago crearPago(Payment payment) {
        Pago pago = new Pago();
        pago.setId(payment.getId().toString());
        pago.setDate( payment.getDateCreated().toLocalDateTime() );
        pago.setState(PaymentState.valueOf(payment.getStatus()));
        pago.setStatusDetail(payment.getStatusDetail());
        pago.setState(PaymentState.valueOf(payment.getStatus().toLowerCase()));
        pago.setCurrency(payment.getCurrencyId());
        pago.setAuthorizationCode(payment.getAuthorizationCode());
        pago.setTransactionValue(payment.getTransactionAmount().floatValue());
        return pago;
    }



}
