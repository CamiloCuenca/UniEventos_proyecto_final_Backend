package co.edu.uniquindio.proyecto.exception.event;

public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String id) {
        super("No se encontró el el evento con el id " + id);
    }
}
