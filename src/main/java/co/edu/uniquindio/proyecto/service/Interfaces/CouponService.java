package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;

import java.util.List;
import java.util.UUID;

public interface CouponService {

    /**
     * Crear un nuevo cupón
     *
     * @param couponDTO Data Transfer Object que contiene los detalles del cupón
     * @return El ID del cupón creado
     * @throws Exception Si no se pudo crear el cupón
     */
    String createCoupon(CouponDTO couponDTO) throws Exception;

    /**
     * Validar un cupón por su código y comprobar si no está expirado
     *
     * @param code Código del cupón
     * @return True si el cupón es válido, false en caso contrario
     * @throws Exception Si el cupón no existe o es inválido
     */
    boolean validateCoupon(String code) throws Exception;

    /**
     * Aplicar un cupón a una orden
     *
     * @param code Código del cupón
     * @param orderId El ID de la orden a la cual se aplicará el cupón
     * @return Monto del descuento aplicado
     * @throws Exception Si el cupón es inválido o está expirado
     */
    double applyCoupon(String code, String orderId) throws Exception;

    /**
     * Obtener todos los cupones disponibles (activos)
     *
     * @return Lista de cupones activos
     */
    List<Coupon> getAvailableCoupons();

    /**
     * Desactivar o eliminar un cupón por su ID
     *
     * @param couponId El ID del cupón a desactivar
     * @throws Exception Si no se pudo encontrar o desactivar el cupón
     */
    void deactivateCoupon(String couponId) throws Exception;

    /**
     * Activar un cupón por su ID
     *
     * @param couponId El ID del cupón a activar
     * @throws Exception Si no se pudo activar el cupón
     */
    void activateCoupon(String couponId) throws Exception;

    /**
     * Actualizar la información de un cupón
     *
     * @param couponId El ID del cupón a actualizar
     * @param couponDTO La nueva información del cupón para actualizar
     * @throws Exception Si no se pudo actualizar el cupón
     */
    void updateCoupon(String couponId, CouponDTO couponDTO) throws Exception;

    /**
     * Método auxiliar para generar un código de cupón aleatorio
     *
     * @return Un código de cupón aleatorio
     * @throws Exception Si no se puede generar el código de cupón
     */
    static String generateRandomCouponCode() throws Exception {
        return null;
    }
}
