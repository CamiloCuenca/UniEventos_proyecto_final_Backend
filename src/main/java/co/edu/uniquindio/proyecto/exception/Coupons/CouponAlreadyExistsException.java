package co.edu.uniquindio.proyecto.exception.Coupons;

public class CouponAlreadyExistsException extends RuntimeException {
    public CouponAlreadyExistsException(String message) {
        super(message);
    }
}
