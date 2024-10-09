package co.edu.uniquindio.proyecto.exception.Coupons;

public class ExpiredCouponException extends RuntimeException {
    public ExpiredCouponException(String message) {
        super(message);
    }
}
