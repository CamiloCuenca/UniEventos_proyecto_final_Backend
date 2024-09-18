package co.edu.uniquindio.proyecto.exception.account;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("La contraseña proporcionada no es válida.");
    }
}
