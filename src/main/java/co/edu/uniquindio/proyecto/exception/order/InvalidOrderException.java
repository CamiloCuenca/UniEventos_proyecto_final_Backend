package co.edu.uniquindio.proyecto.exception.order;

public class InvalidOrderException extends RuntimeException {
    public InvalidOrderException(String message) {
        super(message);
    }
}
