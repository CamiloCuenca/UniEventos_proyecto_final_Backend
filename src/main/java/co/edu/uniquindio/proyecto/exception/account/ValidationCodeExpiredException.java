package co.edu.uniquindio.proyecto.exception.account;

public class ValidationCodeExpiredException extends Exception {

    public ValidationCodeExpiredException() {
        super("El código de validación ha expirado.");
    }

    public ValidationCodeExpiredException(String message) {
        super(message);
    }
}