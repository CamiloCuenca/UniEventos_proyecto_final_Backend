package co.edu.uniquindio.proyecto.exception.account;

public class ValidationCodeNotFoundException extends RuntimeException {
    public ValidationCodeNotFoundException(String message) {
        super(message);
    }
}
