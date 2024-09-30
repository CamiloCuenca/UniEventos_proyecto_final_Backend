package co.edu.uniquindio.proyecto.model.Coupon;

import co.edu.uniquindio.proyecto.Enum.CouponStatus;
import co.edu.uniquindio.proyecto.Enum.TypeCoupon;
import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;
import co.edu.uniquindio.proyecto.service.Interfaces.CouponService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CouponTest {

    // Inyección del servicio de cupones para realizar las pruebas
    @Autowired
    private CouponService couponService;

    // Prueba para crear un cupón
    // Se crea un objeto CouponDTO con los datos del cupón y se llama al método createCoupon del servicio
    @Test
    public void createCouponTest() throws Exception {
        // Crear un nuevo objeto CouponDTO con nombre, código, descuento, fecha de expiración, estado y tipo
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // La fecha de expiración es en 30 días
        CouponDTO couponDTO = new CouponDTO(
                "Amor y la Amistad",     // Nombre del cupón
                "12345",                 // Código del cupón
                "10",                    // Descuento del cupón
                expirationDate,          // Fecha de expiración
                CouponStatus.NOT_AVAILABLE, // Estado del cupón
                TypeCoupon.MULTIPLE,     // Tipo de cupón
                null,                    // El cupon no está asociado a un evento específico
                expirationDate           // Se usa expirationDate como endDate
        );

        // Llamar al servicio para crear el cupón
        couponService.createCoupon(couponDTO);
    }

    // Prueba para crear un cupón específico para un evento
    @Test
    public void createEventSpecificCouponTest() throws Exception {
        // ID y nombre del evento específico
        String eventId = "66f5c5a0de22e82833106d93";
        String eventName = "Concierto Rock 2024";

        // Crear un nuevo objeto CouponDTO con nombre, código, descuento, fecha de expiración, estado, tipo y evento asociado
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(30); // La fecha de expiración es en 30 días
        CouponDTO couponDTO = new CouponDTO(
                "Descuento Concierto Rock",  // Nombre del cupón
                "ROCK2024",                  // Código del cupón
                "15",                        // Descuento del 15%
                expirationDate,              // Fecha de expiración
                CouponStatus.AVAILABLE,      // Estado del cupón
                TypeCoupon.ONLY,           // Tipo de cupón ONLY
                eventId,                     // ID del evento asociado
                expirationDate               // Se usa expirationDate como endDate
        );

        // Llamar al servicio para crear el cupón
        couponService.createCoupon(couponDTO);
    }

    // Prueba para validar un cupón por su código
    // Se utiliza el servicio para validar el cupón, dado un código
    @Test
    public void validateCouponTest() throws Exception {
        String code = "ROCK2024"; // Código del cupón a validar
        // Llamar al servicio para validar el cupón con el código proporcionado
        couponService.validateCoupon(code);
    }

    // Prueba para obtener los cupones disponibles
    // Si la lista de cupones está vacía, lanza una excepción; si no, imprime la lista de cupones disponibles
    @Test
    public void getAvailableCouponsTest() throws Exception {
        // Obtener la lista de cupones disponibles llamando al servicio
        List<Coupon> couponList = couponService.getAvailableCoupons();
        // Verificar si la lista está vacía, lanzar una excepción si no hay cupones válidos
        if (couponList.isEmpty()) {
            throw new Exception("No hay cupones válidos");
        }
        // Imprimir la lista de cupones disponibles (provisional)
        System.out.println(Arrays.toString(couponList.toArray()));
    }

    // Prueba para desactivar un cupón
    // Se desactiva un cupón por su ID utilizando el servicio
    @Test
    public void deactivateCouponTest() throws Exception {
        String couponId = "66e8865bdbc7b554d5bbc780"; // ID del cupón a desactivar
        // Llamar al servicio para desactivar el cupón
        couponService.deactivateCoupon(couponId);
    }

    // Prueba para activar un cupón
    // Se activa un cupón por su ID utilizando el servicio
    @Test
    public void activateCouponTest() throws Exception {
        String couponId = "66e8865bdbc7b554d5bbc780"; // ID del cupón a activar
        // Llamar al servicio para activar el cupón
        couponService.activateCoupon(couponId);
    }
}
