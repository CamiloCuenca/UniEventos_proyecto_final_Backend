package co.edu.uniquindio.proyecto.exception.account;

public class ValidationCodeNotAssociatedException extends RuntimeException {
    public ValidationCodeNotAssociatedException(String message) {
        super(message);
    }
}
