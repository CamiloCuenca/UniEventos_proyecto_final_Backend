package co.edu.uniquindio.proyecto.exception.account;

public class InvalidadEmailException extends RuntimeException {
    public InvalidadEmailException() {
        super("El email incorrecto");
    }
}
