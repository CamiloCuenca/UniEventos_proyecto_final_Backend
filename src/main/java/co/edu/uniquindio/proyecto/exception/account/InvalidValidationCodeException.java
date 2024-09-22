package co.edu.uniquindio.proyecto.exception.account;

public class InvalidValidationCodeException extends Exception {

    public InvalidValidationCodeException() {
        super("El código de validación es inválido.");
    }

    public InvalidValidationCodeException(String message) {
        super(message);
    }
}
