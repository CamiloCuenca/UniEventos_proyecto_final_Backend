package co.edu.uniquindio.proyecto.exception;

public class CedulaAlreadyExistsException extends RuntimeException {
    public CedulaAlreadyExistsException(String cedula) {
        super("la cedula ya existe: " + cedula);
    }
}
