package co.edu.uniquindio.proyecto.service.Interfaces;

import co.edu.uniquindio.proyecto.dto.Coupon.CouponDTO;
import co.edu.uniquindio.proyecto.model.Coupons.Coupon;

import java.util.List;
import java.util.UUID;

public interface CouponService {
    /**
     * Create a new coupon
     *
     * @param couponDTO Data Transfer Object containing coupon details
     * @return The created Coupon ID
     * @throws Exception If the coupon could not be created
     */
    String createCoupon(CouponDTO couponDTO) throws Exception;

    /**
     * Validate a coupon by its code and check if it is not expired
     *
     * @param code Coupon code
     * @return True if the coupon is valid, false otherwise
     * @throws Exception If the coupon does not exist or is invalid
     */
    boolean validateCoupon(String code) throws Exception;

    /**
     * Apply a coupon to an order
     *
     * @param code Coupon code
     * @param orderId The ID of the order to apply the coupon to
     * @return Discount amount applied
     * @throws Exception If the coupon is invalid or expired
     */
    double applyCoupon(String code, String orderId) throws Exception;

    /**
     * Get all available (active) coupons
     *
     * @return List of active coupons
     */
    List<Coupon> getAvailableCoupons();

    /**
     * Deactivate or delete a coupon by its ID
     *
     * @param couponId The ID of the coupon to deactivate
     * @throws Exception If the coupon could not be found or deactivated
     */
    void deactivateCoupon(String couponId) throws Exception;


    /** Activate a coupon by its ID
     *
     * @param couponId
     * @throws Exception
     */
    void activateCoupon(String couponId) throws Exception;

    /**
     * Update coupon information
     *
     * @param couponId The ID of the coupon to update
     * @param couponDTO The new coupon information to update
     * @throws Exception If the coupon could not be updated
     */
    void updateCoupon(String couponId, CouponDTO couponDTO) throws Exception;


    // Método auxiliar para generar un código de cupón aleatorio
    static String generateRandomCouponCode() throws Exception {
        return null;
    }


}
