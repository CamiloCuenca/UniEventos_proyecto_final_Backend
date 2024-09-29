package co.edu.uniquindio.proyecto.exception.account;

public class AccountAlreadyActiveException extends RuntimeException {
    public AccountAlreadyActiveException(String message) {
        super(message);
    }
}
