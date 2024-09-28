package co.edu.uniquindio.proyecto.exception.account;

public class ActiveAccountException extends RuntimeException {
    public ActiveAccountException(String message) {
        super(message);
    }
}
