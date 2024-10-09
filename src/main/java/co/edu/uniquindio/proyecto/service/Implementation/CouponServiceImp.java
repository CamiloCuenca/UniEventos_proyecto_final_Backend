package co.edu.uniquindio.proyecto.service.Implementation;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.EventStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.exception.Coupons.*;
import co.edu.uniquindio.proyecto.exception.OrdenNotFoundExcepcion;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.model.Events.Event;
import co.edu.uniquindio.proyecto.model.PurchaseOrder.Order;
import co.edu.uniquindio.proyecto.repository.CouponRepository;
import co.edu.uniquindio.proyecto.repository.EventRepository;
import co.edu.uniquindio.proyecto.repository.OrderRepository;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CouponServiceImp implements CouponService {

    private final CouponRepository couponRepository;
    private final OrderRepository orderRepository;
    private final EventRepository eventRepository;


    /**
     * Metodo para crear un cupon.
     *
     * @param couponDTO Data Transfer Object containing coupon details
     * @return
     * @throws Exception
     */
    @Override
    public String createCoupon(CouponDTO couponDTO) throws CouponAlreadyExistsException, ExpiredCouponException, DateInvalideCouponException, CouponInvalideForEventExcepcion {
        // Verificar si el código del cupón ya existe en la base de datos
        Coupon existingCoupon = couponRepository.findByCode(couponDTO.code());
        if (existingCoupon != null) {
            // Si existe, lanzar una excepción indicando que ya existe un cupón con ese código
            throw new CouponAlreadyExistsException("Ya existe un cupón con el código: " + couponDTO.code());
        }

        // Comprobar si la fecha de expiración es válida
        if (couponDTO.expirationDate() != null && couponDTO.expirationDate().isBefore(LocalDateTime.now())) {
            // Si la fecha de expiración es anterior a la fecha actual, lanzar una excepción
            throw new ExpiredCouponException("La fecha de expiración no puede estar en el pasado.");
        }

        // Validar las fechas de inicio y expiración del cupón
        if (couponDTO.startDate() != null && couponDTO.expirationDate() != null) {
            // Si la fecha de inicio es posterior a la fecha de expiración, lanzar una excepción
            if (couponDTO.startDate().isAfter(couponDTO.expirationDate())) {
                throw new DateInvalideCouponException("La fecha de inicio no puede ser posterior a la fecha de fin.");
            }
        }

        // Validar el evento asociado al cupón
        if (couponDTO.eventId() != null) {
            // Buscar el evento utilizando el ID proporcionado
            Event event = eventRepository.findById(couponDTO.eventId())
                    .orElseThrow(() -> new CouponNotFountException("El evento asociado al cupón no existe"));

            // Verificar el estado del evento
            if (event.getStatus() == EventStatus.INACTIVE) {
                // Si el evento está inactivo, lanzar una excepción
                throw new CouponInvalideForEventExcepcion("No se puede aplicar un cupón para un evento inactivo.");
            }
        }

        // Crear una nueva instancia de Coupon
        Coupon newCoupon = new Coupon();
        newCoupon.setName(couponDTO.name());  // Asignar el nombre del cupón
        newCoupon.setCode(generateRandomCouponCode());  // Generar un código único para el cupón
        newCoupon.setDiscount(couponDTO.discount());  // Asignar el porcentaje de descuento
        newCoupon.setExpirationDate(couponDTO.expirationDate());  // Asignar la fecha de expiración
        newCoupon.setStatus(couponDTO.status());  // Asignar el estado del cupón
        newCoupon.setType(couponDTO.type());  // Asignar el tipo de cupón

        // Asignar el ID del evento si se especificó en el DTO
        if (couponDTO.eventId() != null) {
            newCoupon.setEventId(couponDTO.eventId());
        }

        // Asignar la fecha de inicio si se especificó en el DTO
        if (couponDTO.startDate() != null) {
            newCoupon.setStartDate(couponDTO.startDate());
        }

        // Guardar el nuevo cupón en la base de datos y devolver su ID
        Coupon createdCoupon = couponRepository.save(newCoupon);
        return createdCoupon.getCouponId();
    }

    /**
     * Metodo para validar que cupon exista.
     *
     * @param code Coupon code
     * @return
     * @throws Exception
     */
    @Override
    public boolean validateCoupon(String code) throws CouponNotFountException {
        // Buscar el cupón en la base de datos utilizando el código proporcionado
        Coupon coupon = couponRepository.findByCode(code);

        // Verificar si el cupón no existe o su estado no es 'AVAILABLE'
        if (coupon == null || coupon.getStatus() != CouponStatus.AVAILABLE) {
            // Si el cupón no se encuentra o no está activo, lanzar una excepción
            throw new CouponNotFountException("El coupon no existe o no esta activo");
        }

        // Si el cupón es válido, retornar verdadero
        return true;
    }


    /**
     * Metodo para aplicar el cupon
     *
     * @param code    Coupon code
     * @param orderId The ID of the order to apply the coupon to
     * @return
     * @throws Exception
     */
    @Override
    public double applyCoupon(String code, String orderId)
            throws CouponExpiredException, CouponNotFountException,
            CouponInvalideForEventExcepcion, CouponIsUsedException,
            OrdenNotFoundExcepcion {

        // Buscar el cupón por su código
        Coupon coupon = couponRepository.findByCode(code);
        if (coupon == null) {
            throw new CouponNotFountException("El cupón no existe.");
        }

        // Validar si el cupón es válido, no expirado y no utilizado
        if (coupon.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new CouponExpiredException("El cupón ha expirado.");
        }

        // Verificar el tipo de cupón (UNICO o MULTIPLE)
        if (coupon.getType() == TypeCoupon.ONLY && coupon.getStatus() == CouponStatus.NOT_AVAILABLE) {
            throw new CouponIsUsedException("El cupón ya ha sido utilizado.");
        }

        // Validar el evento asociado al cupón
        if (coupon.getEventId() != null) {
            Event event = eventRepository.findById(coupon.getEventId())
                    .orElseThrow(() -> new CouponNotFountException("El evento asociado al cupón no existe"));

            // Verificar si el evento está inactivo
            if (event.getStatus() == EventStatus.INACTIVE) {
                throw new CouponInvalideForEventExcepcion("No se puede aplicar un cupón para un evento inactivo.");
            }
        }

        // Buscar la orden por su ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrdenNotFoundExcepcion("La orden no existe"));

        // Aplicar el cupón a la orden
        double discount = applyDiscountToOrder(order, coupon);

        // Marcar el cupón como usado si es UNICO
        if (coupon.getType() == TypeCoupon.ONLY) {
            coupon.setStatus(CouponStatus.NOT_AVAILABLE);
            couponRepository.save(coupon);
        }

        // Guardar la orden actualizada
        orderRepository.save(order);

        // Retornar el valor del descuento aplicado
        return discount;
    }

    /** metodo que retorna una lista de los cupones activos
     *
     * @return lista de cupones activos
     */
    @Override
    public List<Coupon> getAvailableCoupons() {
        return couponRepository.findAvailableCoupons();
    }

    /**
     * Metodo para desactivar el cupon.
     *
     * @param couponId The ID of the coupon to deactivate
     * @throws Exception
     */
    @Override
    public void deactivateCoupon(String couponId) throws CouponNotFountException {
        // Buscar el cupón por su ID y lanzar una excepción si no existe
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFountException("El cupón no existe"));

        // Cambiar el estado del cupón a NO_DISPONIBLE
        coupon.setStatus(CouponStatus.NOT_AVAILABLE);

        // Guardar el cupón actualizado en la base de datos
        couponRepository.save(coupon);
    }

    /**
     * Metodo para activar el cupon
     *
     * @param couponId
     * @throws Exception
     */
    @Override
    public void activateCoupon(String couponId) throws CouponNotFountException {
        // Buscar el cupón por su ID y lanzar una excepción si no existe
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new CouponNotFountException("El cupón no existe"));
        // Cambiar el estado del cupón a DISPONIBLE
        coupon.setStatus(CouponStatus.AVAILABLE);

        // Guardar el cupón actualizado en la base de datos
        couponRepository.save(coupon);

    }


    /**
     * Metodo que se encarga de actualizar el cupon.
     *
     * @param couponId  The ID of the coupon to update
     * @param couponDTO The new coupon information to update
     * @throws Exception
     */
    @Override
    public void updateCoupon(String couponId, CouponDTO couponDTO) throws CouponNotFountException {
        // Buscar el cupón en el repositorio utilizando el ID proporcionado
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);

        // Verificar si el cupón existe
        if (optionalCoupon.isEmpty()) {
            // Si no se encuentra el cupón, lanzar una excepción personalizada
            throw new CouponNotFountException("El coupon no existe");
        }

        // Obtener el cupón existente
        Coupon coupon = optionalCoupon.get();

        // Actualizar los atributos del cupón con la información del DTO proporcionado
        coupon.setName(couponDTO.name());
        coupon.setCode(couponDTO.code());
        coupon.setDiscount(couponDTO.discount());
        coupon.setExpirationDate(couponDTO.expirationDate());
        coupon.setStatus(couponDTO.status());

        // Guardar los cambios en el repositorio
        couponRepository.save(coupon);
    }

    /**
     * Metodo que se encarga de aplicar un descuento a la orden.
     *
     * @param order
     * @param coupon
     * @return
     */
    private double applyDiscountToOrder(Order order, Coupon coupon) {
        // Lógica para calcular el descuento basado en el tipo de cupón
        double discountPercentage = Integer.parseInt(coupon.getDiscount()) / 100.0;

        // Calcular el monto del descuento
        double discountAmount = order.getTotal() * discountPercentage;

        // Actualizar el total de la orden
        order.setTotal(order.getTotal() - discountAmount);


        return discountAmount;
    }


    /** Método auxiliar para generar un código de cupón aleatorio
     *
     * @return codigo generado aleatoriamente
     */
    public static String generateRandomCouponCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();  // Código aleatorio de 8 caracteres
    }

}
