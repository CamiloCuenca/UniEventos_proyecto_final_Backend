package co.edu.uniquindio.proyecto.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("La contraseña proporcionada no es válida.");
    }
}
