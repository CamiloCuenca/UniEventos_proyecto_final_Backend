package co.edu.uniquindio.proyecto.exception.event;

public class NoEventsFoundException extends RuntimeException {
    public NoEventsFoundException() {
        super("No se encontraron los Eventos en el sistema");
    }
}
