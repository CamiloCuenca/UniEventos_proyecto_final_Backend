package co.edu.uniquindio.proyecto.exception.Coupons;

public class CouponIsUsedException extends RuntimeException {
    public CouponIsUsedException(String message) {
        super(message);
    }
}
