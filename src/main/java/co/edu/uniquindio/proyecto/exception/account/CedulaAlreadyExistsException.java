package co.edu.uniquindio.proyecto.exception.account;

public class CedulaAlreadyExistsException extends RuntimeException {
    public CedulaAlreadyExistsException(String idNumber) {
        super("la cedula ya existe: " + idNumber);
    }
}
