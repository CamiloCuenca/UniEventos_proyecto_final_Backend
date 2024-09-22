package co.edu.uniquindio.proyecto.exception.event;

public class EventRetrievalException extends RuntimeException {
    public EventRetrievalException(String message) {
        super("Error al recuperar las cuentas: " + message);
    }
}
