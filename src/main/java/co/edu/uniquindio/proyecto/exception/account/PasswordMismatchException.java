package co.edu.uniquindio.proyecto.exception.account;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {
        super(message);
    }
}
