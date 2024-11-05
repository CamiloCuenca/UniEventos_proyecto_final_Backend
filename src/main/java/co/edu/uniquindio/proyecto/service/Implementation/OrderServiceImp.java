package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.PaymentState;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Account.LoginDTO;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.dto.EmailDTO;
import co.edu.uniquindio.proyecto.dto.Order.OrderDTO;
import co.edu.uniquindio.proyecto.dto.Order.dtoOrderFilter;
import co.edu.uniquindio.proyecto.exception.event.EventNotFoundException;
import co.edu.uniquindio.proyecto.exception.order.FirstOrderException;
import co.edu.uniquindio.proyecto.exception.order.InvalidOrderException;
import co.edu.uniquindio.proyecto.exception.order.OrderNotFoundException;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import org.springframework.data.mongodb.core.query.Query;

import javax.security.auth.login.AccountNotFoundException;
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
    private final PasswordEncoder passwordEncoder;


    /**
     * Este mètodo crea un orden de compra segun los datos de orderDTO
     *
     * @param orderDTO Objeto OrderDTO que contiene los detalles de la nueva orden.
     * @return orden creada
     * @throws Exception
     */
    @Override
    public Order createOrder(OrderDTO orderDTO) throws InvalidOrderException, AccountNotFoundException, EventNotFoundException, FirstOrderException, Exception {
        // Validar que el OrderDTO no sea nulo
        if (orderDTO == null) {
            throw new InvalidOrderException("No se puede crear una orden: OrderDTO es nulo");
        }

        // Validar que los elementos de la orden no sean nulos ni vacíos
        if (orderDTO.items() == null || orderDTO.items().isEmpty()) {
            throw new InvalidOrderException("No se pueden crear órdenes sin artículos");
        }

        // Calcular el total de la orden
        double total = 0;
        for (OrderDetail item : orderDTO.items()) {
            if (item.getPrice() <= 0) {
                throw new InvalidOrderException("El precio del artículo debe ser positivo");
            }
            if (item.getAmount() <= 0) {
                throw new InvalidOrderException("La cantidad del artículo debe ser positiva");
            }
            total += item.getPrice() * item.getAmount();
        }

        // Validar que el total calculado coincida con el total proporcionado
        if (total != orderDTO.total()) {
            throw new InvalidOrderException("El total calculado no coincide con el total proporcionado");
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
                .codeCoupon(orderDTO.codeCoupon() != null ? orderDTO.codeCoupon() : null)  // Manejar el ID del cupón opcionalmente
                .build();

        // Verificar si la cuenta existe
        if (cuentaRepo.findById(orderDTO.idAccount()).isEmpty()) {
            throw new AccountNotFoundException("El ID de la cuenta no existe");
        }

        // Verificar si el evento existe
        if (eventRepository.findById(String.valueOf(orderDTO.items().get(0).getIdEvent())).isEmpty()) {
            throw new EventNotFoundException("El ID del evento no existe");
        }

        // Verificar si es la primera orden de la cuenta
        List<Order> ordersByAccount = orderRepository.findByAccountId(orderDTO.idAccount());
        if (ordersByAccount.isEmpty()) {
            // Esta es la primera orden para la cuenta
            System.out.println("¡Esta es la primera orden para la cuenta: " + orderDTO.idAccount() + "!");
            Optional<Account> optionalAccount = cuentaRepo.findById(orderDTO.idAccount());
            sendCupon(optionalAccount.get().getEmail());
            // Lanzar excepción si es necesario (si hay lógica especial para la primera orden)
            throw new FirstOrderException("Se ha creado la primera orden para la cuenta: " + orderDTO.idAccount());
        }

        // Guardar la orden en la base de datos
        Order savedOrder = orderRepository.save(order);

        // Aplicar cupón si está presente
        if (orderDTO.codeCoupon() != null) {
            couponServiceImp.applyCoupon(orderDTO.codeCoupon(), savedOrder.getId());
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
            throw new AccountNotFoundException("El email no existe para la cuenta con ID: " + order.getIdAccount());
        }

        return savedOrder;
    }


    /**
     * Mètodo auxiliar para crear el cupon de bienvenida ( tiene parametros fijos)
     *
     * @param email
     * @throws Exception
     */
    private void sendCupon(String email) throws Exception {
        CouponDTO couponDTO = new CouponDTO(
                "Cupón de Bienvenida",              // Nombre del cupón
                CouponServiceImp.generateRandomCouponCode(),         // Código único de cupón
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

    /**
     * Este mètodo actualiza una orden existente.
     *
     * @param orderId         ID de la orden que se desea actualizar.
     * @param updatedOrderDTO Objeto OrderDTO que contiene los datos actualizados de la orden.
     * @return
     * @throws Exception
     */
    @Override
    public Order updateOrder(String orderId, OrderDTO updatedOrderDTO) throws IllegalArgumentException, Exception {
        // Validar orderId
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("El ID de la orden no puede ser nulo o vacío");
        }

        // Validar updatedOrderDTO
        if (updatedOrderDTO == null) {
            throw new IllegalArgumentException("El DTO de orden actualizado no puede ser nulo");
        }

        // Validar campos de updatedOrderDTO
        if (updatedOrderDTO.total() <= 0) {
            throw new IllegalArgumentException("El total debe ser un valor positivo");
        }

        // Verificar si la orden existe
        Optional<Order> existingOrder = orderRepository.findById(orderId);
        if (existingOrder.isEmpty()) {
            throw new Exception("No se puede actualizar una orden que no existe");
        }

        Order order = existingOrder.get();
        order.setTotal(updatedOrderDTO.total());
        order.setItems(updatedOrderDTO.items());
        order.setPayment(updatedOrderDTO.payment().toEntity());  // Convertir el PaymentDTO a Payment

        // Aplicar el cupón si es que se proporcionó un nuevo cupón
        if (updatedOrderDTO.codeCoupon() != null) {
            couponServiceImp.applyCoupon(updatedOrderDTO.codeCoupon(), order.getId());
        }

        return orderRepository.save(order);
    }

    /**
     * Este mètodo elimina una orden de compra
     *
     * @param orderId ID de la orden que se desea eliminar.
     * @throws Exception
     */
    @Override
    public void deleteOrder(String orderId) throws InvalidOrderException, Exception, OrderNotFoundException {
        // Validar orderId
        if (orderId == null || orderId.isBlank()) {
            throw new InvalidOrderException("El ID de la orden no puede ser nulo o vacío");
        }

        // Verificar si la orden existe
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new OrderNotFoundException("No se puede eliminar una orden que no existe");
        }

        // Eliminar la orden
        orderRepository.deleteById(orderId);
    }


    /**
     * Listar todas las órdenes de una cuenta específica.
     *
     * @param accountId ID de la cuenta del usuario.
     * @return
     * @throws Exception
     */
    @Override
    public List<Order> getOrdersByUser(String accountId) throws Exception {
        return orderRepository.findByAccountId(accountId);
    }

    /**
     * Listar todas las órdenes.
     *
     * @return lista de todas las ordenes
     * @throws Exception
     */
    @Override
    public List<Order> getAllOrders() throws Exception {
        return orderRepository.findAll();
    }

    /**
     * Realizar el pago de una orden mediante MercadoPago.
     *
     * @param idOrden ID de la orden para la cual se realiza el pago.
     * @return Preference que contiene los detalles de la preferencia de pago creada.
     * @throws Exception
     */
    @Override
    public Preference realizarPago(String idOrden) throws Exception {

        // Obtener la orden guardada en la base de datos y los ítems de la orden
        Order ordenGuardada = obtenerOrden(idOrden);
        List<PreferenceItemRequest> itemsPasarela = new ArrayList<>();

        // Recorrer los items de la orden y crear los ítems de la pasarela
        for (OrderDetail item : ordenGuardada.getItems()) {

            // Obtener el evento y la localidad del ítem
            Event evento = eventService.obtenerEvento(item.getIdEvent().toString());
            Locality localidad = evento.obtenerLocalidad(item.getLocalityName());

            // Calcular los boletos disponibles
            int boletosDisponibles = localidad.getMaximumCapacity() - localidad.getTicketsSold();

            // Verificar si la cantidad solicitada supera la capacidad disponible
            if (item.getAmount() > boletosDisponibles) {
                throw new Exception("No hay suficientes boletos disponibles para la localidad: "
                        + localidad.getName() + ". Boletos disponibles: " + boletosDisponibles);
            }

            // Crear el ítem de la pasarela
            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .id(evento.getId())
                    .title(evento.getName())
                    .pictureUrl(evento.getCoverImage())
                    .categoryId(evento.getType().name())
                    .quantity(item.getAmount())
                    .currencyId("COP")
                    .unitPrice(BigDecimal.valueOf(localidad.getPrice()))
                    .build();

            itemsPasarela.add(itemRequest);
        }

        // Configurar las credenciales de MercadoPago
        MercadoPagoConfig.setAccessToken("APP_USR-1074363858207208-100622-1c36028d107a18a9507c21ceadc5069e-2021909487");

        // Configurar las URLs de retorno de la pasarela (Frontend)
        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success("URL PAGO EXITOSO")
                .failure("URL PAGO FALLIDO")
                .pending("URL PAGO PENDIENTE")
                .build();

        // Construir la preferencia de la pasarela con los ítems, metadatos y URLs de retorno
        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .backUrls(backUrls)
                .items(itemsPasarela)
                .metadata(Map.of("id_orden", ordenGuardada.getId()))
                .notificationUrl("https://3d0a-2800-e2-7180-1775-463-594c-d6d-44a4.ngrok-free.app/api/orden/notificacion-pago")
                .build();

        // Crear la preferencia en la pasarela de MercadoPago
        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        // Guardar el código de la pasarela en la orden
        ordenGuardada.setCodigoPasarela(preference.getId());
        orderRepository.save(ordenGuardada);

        // Aquí asumimos que el pago fue exitoso y se confirma en la notificación de la pasarela
        for (OrderDetail item : ordenGuardada.getItems()) {
            Event evento = eventService.obtenerEvento(item.getIdEvent().toString());
            Locality localidad = evento.obtenerLocalidad(item.getLocalityName());

            // Aumentar el número de boletos vendidos solo si el pago es exitoso
            localidad.setTicketsSold(localidad.getTicketsSold() + item.getAmount());

            // Verificar si ya se ha alcanzado la capacidad máxima de la localidad
            if (localidad.getTicketsSold() >= localidad.getMaximumCapacity()) {
                System.out.println("¡La localidad '" + localidad.getName() + "' ha agotado sus boletos!");
            }

            // Actualizar el evento en la base de datos con los nuevos boletos vendidos
            updateEvent(evento);
        }

        return preference;
    }

    public void updateEvent(Event evento) {
        // Buscar el evento por su ID en la base de datos para verificar su existencia
        Optional<Event> optionalEvent = eventRepository.findById(evento.getId());

        // Verificar si el evento existe
        if (optionalEvent.isEmpty()) {
            throw new EventNotFoundException(evento.getId()); // Lanza una excepción si el evento no existe
        }

        // Guardar el evento actualizado en la base de datos
        eventRepository.save(evento);

        System.out.println("El evento ha sido actualizado correctamente en la base de datos.");
    }

    /**
     * Recibir y manejar la notificación de MercadoPago.
     *
     * @param request Mapa que contiene la información de la notificación recibida.
     */
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
                Payment payment = client.get(Long.parseLong(idPago));


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


    /**
     * Obtener una orden por su ID.
     *
     * @param idOrden ID de la orden que se desea obtener.
     * @return
     * @throws Exception
     */
    @Override
    public Order obtenerOrden(String idOrden) throws OrderNotFoundException {
        Optional<Order> orderOptional = orderRepository.findById(idOrden);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        } else {
            throw new OrderNotFoundException("Orden no encontrada con ID: " + idOrden);
        }
    }

    /**
     * Filtrar órdenes según su estado de pago.
     *
     * @param filter Objeto dtoOrderFilter que contiene los criterios de filtrado.
     * @return
     */
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



    /**
     * Este mètodo crea el pago segun los datos resividos por mercado pago
     *
     * @param payment
     * @return
     */
    private Pago crearPago(Payment payment) {
        Pago pago = new Pago();
        pago.setId(payment.getId().toString());
        pago.setDate(payment.getDateCreated().toLocalDateTime());
        pago.setState(PaymentState.valueOf(payment.getStatus()));
        pago.setStatusDetail(payment.getStatusDetail());
        pago.setState(PaymentState.valueOf(payment.getStatus().toLowerCase()));
        pago.setCurrency(payment.getCurrencyId());
        pago.setAuthorizationCode(payment.getAuthorizationCode());
        pago.setTransactionValue(payment.getTransactionAmount().floatValue());
        return pago;
    }




}
